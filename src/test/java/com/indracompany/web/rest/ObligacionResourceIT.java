package com.indracompany.web.rest;

import com.indracompany.ObligacionesApp;
import com.indracompany.domain.Obligacion;
import com.indracompany.repository.ObligacionRepository;
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

import com.indracompany.domain.enumeration.Estado;
/**
 * Integration tests for the {@link ObligacionResource} REST controller.
 */
@SpringBootTest(classes = ObligacionesApp.class)
public class ObligacionResourceIT {

    private static final String DEFAULT_CODIGO_OBLIGACION = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_OBLIGACION = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_VALOR_TOTAL = 1;
    private static final Integer UPDATED_VALOR_TOTAL = 2;

    private static final Integer DEFAULT_VALOR_PERIODO = 1;
    private static final Integer UPDATED_VALOR_PERIODO = 2;

    private static final Integer DEFAULT_NOMERO_PERIODOS = 1;
    private static final Integer UPDATED_NOMERO_PERIODOS = 2;

    private static final Integer DEFAULT_PERIODO_ACTUAL = 1;
    private static final Integer UPDATED_PERIODO_ACTUAL = 2;

    private static final Boolean DEFAULT_VIGENTE = false;
    private static final Boolean UPDATED_VIGENTE = true;

    private static final Estado DEFAULT_ESTADO = Estado.PAGADO;
    private static final Estado UPDATED_ESTADO = Estado.PENDIENTE;

    @Autowired
    private ObligacionRepository obligacionRepository;

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

    private MockMvc restObligacionMockMvc;

    private Obligacion obligacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObligacionResource obligacionResource = new ObligacionResource(obligacionRepository);
        this.restObligacionMockMvc = MockMvcBuilders.standaloneSetup(obligacionResource)
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
    public static Obligacion createEntity(EntityManager em) {
        Obligacion obligacion = new Obligacion()
            .codigoObligacion(DEFAULT_CODIGO_OBLIGACION)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fecha(DEFAULT_FECHA)
            .valorTotal(DEFAULT_VALOR_TOTAL)
            .valorPeriodo(DEFAULT_VALOR_PERIODO)
            .nomeroPeriodos(DEFAULT_NOMERO_PERIODOS)
            .periodoActual(DEFAULT_PERIODO_ACTUAL)
            .vigente(DEFAULT_VIGENTE)
            .estado(DEFAULT_ESTADO);
        return obligacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Obligacion createUpdatedEntity(EntityManager em) {
        Obligacion obligacion = new Obligacion()
            .codigoObligacion(UPDATED_CODIGO_OBLIGACION)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fecha(UPDATED_FECHA)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .valorPeriodo(UPDATED_VALOR_PERIODO)
            .nomeroPeriodos(UPDATED_NOMERO_PERIODOS)
            .periodoActual(UPDATED_PERIODO_ACTUAL)
            .vigente(UPDATED_VIGENTE)
            .estado(UPDATED_ESTADO);
        return obligacion;
    }

    @BeforeEach
    public void initTest() {
        obligacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createObligacion() throws Exception {
        int databaseSizeBeforeCreate = obligacionRepository.findAll().size();

        // Create the Obligacion
        restObligacionMockMvc.perform(post("/api/obligacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obligacion)))
            .andExpect(status().isCreated());

        // Validate the Obligacion in the database
        List<Obligacion> obligacionList = obligacionRepository.findAll();
        assertThat(obligacionList).hasSize(databaseSizeBeforeCreate + 1);
        Obligacion testObligacion = obligacionList.get(obligacionList.size() - 1);
        assertThat(testObligacion.getCodigoObligacion()).isEqualTo(DEFAULT_CODIGO_OBLIGACION);
        assertThat(testObligacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testObligacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testObligacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testObligacion.getValorTotal()).isEqualTo(DEFAULT_VALOR_TOTAL);
        assertThat(testObligacion.getValorPeriodo()).isEqualTo(DEFAULT_VALOR_PERIODO);
        assertThat(testObligacion.getNomeroPeriodos()).isEqualTo(DEFAULT_NOMERO_PERIODOS);
        assertThat(testObligacion.getPeriodoActual()).isEqualTo(DEFAULT_PERIODO_ACTUAL);
        assertThat(testObligacion.isVigente()).isEqualTo(DEFAULT_VIGENTE);
        assertThat(testObligacion.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createObligacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = obligacionRepository.findAll().size();

        // Create the Obligacion with an existing ID
        obligacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObligacionMockMvc.perform(post("/api/obligacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obligacion)))
            .andExpect(status().isBadRequest());

        // Validate the Obligacion in the database
        List<Obligacion> obligacionList = obligacionRepository.findAll();
        assertThat(obligacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllObligacions() throws Exception {
        // Initialize the database
        obligacionRepository.saveAndFlush(obligacion);

        // Get all the obligacionList
        restObligacionMockMvc.perform(get("/api/obligacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obligacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoObligacion").value(hasItem(DEFAULT_CODIGO_OBLIGACION)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL)))
            .andExpect(jsonPath("$.[*].valorPeriodo").value(hasItem(DEFAULT_VALOR_PERIODO)))
            .andExpect(jsonPath("$.[*].nomeroPeriodos").value(hasItem(DEFAULT_NOMERO_PERIODOS)))
            .andExpect(jsonPath("$.[*].periodoActual").value(hasItem(DEFAULT_PERIODO_ACTUAL)))
            .andExpect(jsonPath("$.[*].vigente").value(hasItem(DEFAULT_VIGENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getObligacion() throws Exception {
        // Initialize the database
        obligacionRepository.saveAndFlush(obligacion);

        // Get the obligacion
        restObligacionMockMvc.perform(get("/api/obligacions/{id}", obligacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(obligacion.getId().intValue()))
            .andExpect(jsonPath("$.codigoObligacion").value(DEFAULT_CODIGO_OBLIGACION))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.valorTotal").value(DEFAULT_VALOR_TOTAL))
            .andExpect(jsonPath("$.valorPeriodo").value(DEFAULT_VALOR_PERIODO))
            .andExpect(jsonPath("$.nomeroPeriodos").value(DEFAULT_NOMERO_PERIODOS))
            .andExpect(jsonPath("$.periodoActual").value(DEFAULT_PERIODO_ACTUAL))
            .andExpect(jsonPath("$.vigente").value(DEFAULT_VIGENTE.booleanValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingObligacion() throws Exception {
        // Get the obligacion
        restObligacionMockMvc.perform(get("/api/obligacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObligacion() throws Exception {
        // Initialize the database
        obligacionRepository.saveAndFlush(obligacion);

        int databaseSizeBeforeUpdate = obligacionRepository.findAll().size();

        // Update the obligacion
        Obligacion updatedObligacion = obligacionRepository.findById(obligacion.getId()).get();
        // Disconnect from session so that the updates on updatedObligacion are not directly saved in db
        em.detach(updatedObligacion);
        updatedObligacion
            .codigoObligacion(UPDATED_CODIGO_OBLIGACION)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fecha(UPDATED_FECHA)
            .valorTotal(UPDATED_VALOR_TOTAL)
            .valorPeriodo(UPDATED_VALOR_PERIODO)
            .nomeroPeriodos(UPDATED_NOMERO_PERIODOS)
            .periodoActual(UPDATED_PERIODO_ACTUAL)
            .vigente(UPDATED_VIGENTE)
            .estado(UPDATED_ESTADO);

        restObligacionMockMvc.perform(put("/api/obligacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedObligacion)))
            .andExpect(status().isOk());

        // Validate the Obligacion in the database
        List<Obligacion> obligacionList = obligacionRepository.findAll();
        assertThat(obligacionList).hasSize(databaseSizeBeforeUpdate);
        Obligacion testObligacion = obligacionList.get(obligacionList.size() - 1);
        assertThat(testObligacion.getCodigoObligacion()).isEqualTo(UPDATED_CODIGO_OBLIGACION);
        assertThat(testObligacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testObligacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testObligacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testObligacion.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);
        assertThat(testObligacion.getValorPeriodo()).isEqualTo(UPDATED_VALOR_PERIODO);
        assertThat(testObligacion.getNomeroPeriodos()).isEqualTo(UPDATED_NOMERO_PERIODOS);
        assertThat(testObligacion.getPeriodoActual()).isEqualTo(UPDATED_PERIODO_ACTUAL);
        assertThat(testObligacion.isVigente()).isEqualTo(UPDATED_VIGENTE);
        assertThat(testObligacion.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingObligacion() throws Exception {
        int databaseSizeBeforeUpdate = obligacionRepository.findAll().size();

        // Create the Obligacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObligacionMockMvc.perform(put("/api/obligacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(obligacion)))
            .andExpect(status().isBadRequest());

        // Validate the Obligacion in the database
        List<Obligacion> obligacionList = obligacionRepository.findAll();
        assertThat(obligacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObligacion() throws Exception {
        // Initialize the database
        obligacionRepository.saveAndFlush(obligacion);

        int databaseSizeBeforeDelete = obligacionRepository.findAll().size();

        // Delete the obligacion
        restObligacionMockMvc.perform(delete("/api/obligacions/{id}", obligacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Obligacion> obligacionList = obligacionRepository.findAll();
        assertThat(obligacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
