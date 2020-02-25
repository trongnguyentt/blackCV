package blackcv.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import blackcv.web.rest.TestUtil;

public class ListReasonDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListReasonDTO.class);
        ListReasonDTO listReasonDTO1 = new ListReasonDTO();
        listReasonDTO1.setId(1L);
        ListReasonDTO listReasonDTO2 = new ListReasonDTO();
        assertThat(listReasonDTO1).isNotEqualTo(listReasonDTO2);
        listReasonDTO2.setId(listReasonDTO1.getId());
        assertThat(listReasonDTO1).isEqualTo(listReasonDTO2);
        listReasonDTO2.setId(2L);
        assertThat(listReasonDTO1).isNotEqualTo(listReasonDTO2);
        listReasonDTO1.setId(null);
        assertThat(listReasonDTO1).isNotEqualTo(listReasonDTO2);
    }
}
