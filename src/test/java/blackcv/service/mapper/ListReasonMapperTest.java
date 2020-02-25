package blackcv.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ListReasonMapperTest {

    private ListReasonMapper listReasonMapper;

    @BeforeEach
    public void setUp() {
        listReasonMapper = new ListReasonMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(listReasonMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(listReasonMapper.fromId(null)).isNull();
    }
}
