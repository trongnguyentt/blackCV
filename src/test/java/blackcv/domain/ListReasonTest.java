package blackcv.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import blackcv.web.rest.TestUtil;

public class ListReasonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListReason.class);
        ListReason listReason1 = new ListReason();
        listReason1.setId(1L);
        ListReason listReason2 = new ListReason();
        listReason2.setId(listReason1.getId());
        assertThat(listReason1).isEqualTo(listReason2);
        listReason2.setId(2L);
        assertThat(listReason1).isNotEqualTo(listReason2);
        listReason1.setId(null);
        assertThat(listReason1).isNotEqualTo(listReason2);
    }
}
