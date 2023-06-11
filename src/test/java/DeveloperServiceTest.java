import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.repository.jdbc.JdbcDeveloperRepositoryImpl;
import com.geopokrovskiy.service.DeveloperService;
import com.geopokrovskiy.service.SkillService;
import com.geopokrovskiy.service.SpecialityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

public class DeveloperServiceTest {
   /* private DeveloperService developerService;
    private SkillService skillService;
    private SpecialityService specialityService;
    private JdbcDeveloperRepositoryImpl developerRepository;

    @Before
    public void setUp(){
        developerService = mock(DeveloperService.class);
        skillService = mock(SkillService.class);
        specialityService = mock(SpecialityService.class);
        developerRepository = new JdbcDeveloperRepositoryImpl();
    }

    @Test
    public void testAddNewDeveloper(){
        Developer developer = new Developer(1L,
                "First Name",
                "Last Name",
                new ArrayList<Skill>(),
                new Speciality("Test Speciality"));
        when(specialityService.getSpecById(1L)).thenReturn(new Speciality());
        when(developerRepository.addNew(developer)).thenReturn(developer);

        Developer result = developerService.addNewDeveloper(developer);

        assertEquals(developer, result);
        assertNotNull(result.getSpeciality());
    }*/

}
