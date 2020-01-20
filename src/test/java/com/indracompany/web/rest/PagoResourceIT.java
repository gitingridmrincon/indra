package com.indracompany.web.rest;

import com.indracompany.ObligacionesApp;
import com.indracompany.domain.Pago;
import com.indracompany.repository.PagoRepository;
import com.indracompany.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.indracompany.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PagoResource} REST controller.
 */
@SpringBootTest(classes = ObligacionesApp.class)
public class PagoResourceIT {

    private static final String DEFAULT_CODIGO_PAGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_PAGO = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALOR_PAGADO = 1;
    private static final Integer UPDATED_VALOR_PAGADO = 2;

    private static final LocalDate DEFAULT_FECHA_PAGO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PAGO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PERIODO_PAGADO = 1;
    private static final Integer UPDATED_PERIODO_PAGADO = 2;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPagoMockMvc;

    private Pago pago;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PagoResource pagoResource = new PagoResource(pagoRepository);
        this.restPagoMockMvc = MockMvcBuilders.standaloneSetup(pagoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pago createEntity(EntityManager em) {
        Pago pago = new Pago()
            .codigoPago(DEFAULT_CODIGO_PAGO)
            .valorPagado(DEFAULT_VALOR_PAGADO)
            .fechaPago(DEFAULT_FECHA_PAGO)
            .periodoPagado(DEFAULT_PERIODO_PAGADO);
        return pago;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pago createUpdatedEntity(EntityManager em) {
        Pago pago = new Pago()
            .codigoPago(UPDATED_CODIGO_PAGO)
            .valorPagado(UPDATED_VALOR_PAGADO)
            .fechaPago(UPDATED_FECHA_PAGO)
            .periodoPagado(UPDATED_PERIODO_PAGADO);
        return pago;
    }

    @BeforeEach
    public void initTest() {
        pago = createEntity(em);
    }

    @Test
    @Transactional
    public void createPago() throws Exception {
        int databaseSizeBeforeCreate = pagoRepository.findAll().size();

        // Create the Pago
        restPagoMockMvc.perform(post("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isCreated());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeCreate + 1);
        Pago testPago = pagoList.get(pagoList.size() - 1);
        assertThat(testPago.getCodigoPago()).isEqualTo(DEFAULT_CODIGO_PAGO);
        assertThat(testPago.getValorPagado()).isEqualTo(DEFAULT_VALOR_PAGADO);
        assertThat(testPago.getFechaPago()).isEqualTo(DEFAULT_FECHA_PAGO);
        assertThat(testPago.getPeriodoPagado()).isEqualTo(DEFAULT_PERIODO_PAGADO);
    }

    @Test
    @Transactional
    public void createPagoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pagoRepository.findAll().size();

        // Create the Pago with an existing ID
        pago.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagoMockMvc.perform(post("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPagos() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        // Get all the pagoList
        restPagoMockMvc.perform(get("/api/pagos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pago.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoPago").value(hasItem(DEFAULT_CODIGO_PAGO)))
            .andExpect(jsonPath("$.[*].valorPagado").value(hasItem(DEFAULT_VALOR_PAGADO)))
            .andExpect(jsonPath("$.[*].fechaPago").value(hasItem(DEFAULT_FECHA_PAGO.toString())))
            .andExpect(jsonPath("$.[*].periodoPagado").value(hasItem(DEFAULT_PERIODO_PAGADO)));
    }
    
    @Test
    @Transactional
    public void getPago() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        // Get the pago
        restPagoMockMvc.perform(get("/api/pagos/{id}", pago.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pago.getId().intValue()))
            .andExpect(jsonPath("$.codigoPago").value(DEFAULT_CODIGO_PAGO))
            .andExpect(jsonPath("$.valorPagado").value(DEFAULT_VALOR_PAGADO))
            .andExpect(jsonPath("$.fechaPago").value(DEFAULT_FECHA_PAGO.toString()))
            .andExpect(jsonPath("$.periodoPagado").value(DEFAULT_PERIODO_PAGADO));
    }

    @Test
    @Transactional
    public void getNonExistingPago() throws Exception {
        // Get the pago
        restPagoMockMvc.perform(get("/api/pagos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePago() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();

        // Update the pago
        Pago updatedPago = pagoRepository.findById(pago.getId()).get();
        // Disconnect from session so that the updates on updatedPago are not directly saved in db
        em.detach(updatedPago);
        updatedPago
            .codigoPago(UPDATED_CODIGO_PAGO)
            .valorPagado(UPDATED_VALOR_PAGADO)
            .fechaPago(UPDATED_FECHA_PAGO)
            .periodoPagado(UPDATED_PERIODO_PAGADO);

        restPagoMockMvc.perform(put("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPago)))
            .andExpect(status().isOk());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
        Pago testPago = pagoList.get(pagoList.size() - 1);
        assertThat(testPago.getCodigoPago()).isEqualTo(UPDATED_CODIGO_PAGO);
        assertThat(testPago.getValorPagado()).isEqualTo(UPDATED_VALOR_PAGADO);
        assertThat(testPago.getFechaPago()).isEqualTo(UPDATED_FECHA_PAGO);
        assertThat(testPago.getPeriodoPagado()).isEqualTo(UPDATED_PERIODO_PAGADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPago() throws Exception {
        int databaseSizeBeforeUpdate = pagoRepository.findAll().size();

        // Create the Pago

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagoMockMvc.perform(put("/api/pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pago)))
            .andExpect(status().isBadRequest());

        // Validate the Pago in the database
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePago() throws Exception {
        // Initialize the database
        pagoRepository.saveAndFlush(pago);

        int databaseSizeBeforeDelete = pagoRepository.findAll().size();

        // Delete the pago
        restPagoMockMvc.perform(delete("/api/pagos/{id}", pago.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pago> pagoList = pagoRepository.findAll();
        assertThat(pagoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
