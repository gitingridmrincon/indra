package com.indracompany.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.indracompany.web.rest.TestUtil;

public class ObligacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Obligacion.class);
        Obligacion obligacion1 = new Obligacion();
        obligacion1.setId(1L);
        Obligacion obligacion2 = new Obligacion();
        obligacion2.setId(obligacion1.getId());
        assertThat(obligacion1).isEqualTo(obligacion2);
        obligacion2.setId(2L);
        assertThat(obligacion1).isNotEqualTo(obligacion2);
        obligacion1.setId(null);
        assertThat(obligacion1).isNotEqualTo(obligacion2);
    }
}
