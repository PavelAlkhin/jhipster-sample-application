package eco.vessel.doc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import eco.vessel.doc.web.rest.TestUtil;

public class FoldersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Folders.class);
        Folders folders1 = new Folders();
        folders1.setId(1L);
        Folders folders2 = new Folders();
        folders2.setId(folders1.getId());
        assertThat(folders1).isEqualTo(folders2);
        folders2.setId(2L);
        assertThat(folders1).isNotEqualTo(folders2);
        folders1.setId(null);
        assertThat(folders1).isNotEqualTo(folders2);
    }
}
