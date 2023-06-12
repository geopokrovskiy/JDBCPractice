import com.geopokrovskiy.model.Developer;
import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.service.DeveloperService;
import com.geopokrovskiy.service.SkillService;
import com.geopokrovskiy.service.SpecialityService;
import com.geopokrovskiy.utils.JdbcUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperServiceTest {

    private DeveloperService developerService;
    private SkillService skillService;
    private SpecialityService specialityService;

    @Before
    public void setUp() {
        this.developerService = new DeveloperService();
    }

    @Test
    public void testAddNewDeveloper() {
        Developer developer = new Developer();
        developer.setFirstName("First Name");
        developer.setLastName("Last Name");

        String sql = "SELECT * FROM developers";
        int numberOfRowsBefore = 0;
        int numberOfRowsAfter = 0;
        try {
            PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            numberOfRowsBefore = resultSet.getRow();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        Developer result = this.developerService.addNewDeveloper(developer);

        try {
            PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            numberOfRowsAfter = resultSet.getRow();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getId());
        assert result.getSpeciality().getId() == 1L;
        Assert.assertNotNull(result.getSkills());
        Assert.assertEquals(Status.ACTIVE, result.getStatus());
        Assert.assertEquals("First Name", developer.getFirstName());
        Assert.assertEquals("Last Name", developer.getLastName());
        Assert.assertEquals(1, numberOfRowsAfter - numberOfRowsBefore);

        this.developerService.delete(result.getId());
    }

    @Test
    public void testGetAllDevs() {
        List<Developer> developers = this.developerService.getAllDevs();
        Assert.assertNotNull(developers);
        for (Developer developer : developers) {
            Assert.assertEquals(developer.getStatus(), Status.ACTIVE);
            Assert.assertNotNull(developer.getId());
            Assert.assertNotNull(developer.getSkills());
            Assert.assertNotNull(developer.getSpeciality());
            Assert.assertNotNull(developer.getFirstName());
            Assert.assertNotNull(developer.getLastName());
        }
    }

    @Test
    public void testGetDevById() {
        Developer illegalDeveloper = this.developerService.getDevById(-1L);
        Assert.assertNull(illegalDeveloper.getId());

        List<Developer> developers = this.developerService.getAllDevs();
        for (Developer developer : developers) {
            Assert.assertEquals(developer, this.developerService.getDevById(developer.getId()));
        }
    }

    @Test
    public void testGetDevSkillsAndUpdateDevSkills() {
        this.skillService = new SkillService();
        Skill testSkill = new Skill("Test Skill");
        this.skillService.addNewSkill(testSkill);
        List<Skill> skillList = new ArrayList<>();
        skillList.add(testSkill);

        Developer developer = new Developer();
        developer.setFirstName("First Name");
        developer.setLastName("Last Name");

        Developer addedDeveloper = this.developerService.addNewDeveloper(developer);

        assert !this.developerService.getDevSkills(addedDeveloper.getId()).contains(testSkill);
        this.developerService.updateDevSkills(skillList, developer.getId());
        assert this.developerService.getDevSkills(addedDeveloper.getId()).contains(testSkill);

        this.skillService.delete(testSkill.getId());
        this.developerService.delete(addedDeveloper.getId());
    }

    @Test
    public void testUpdateDevFirstName() {
        Developer developer = new Developer();

        String oldFirstName = "First Name";
        String oldLastName = "Last Name";
        String newFirstName = "New First Name";

        developer.setFirstName(oldFirstName);
        developer.setLastName(oldLastName);

        Developer addedDeveloper = this.developerService.addNewDeveloper(developer);
        Long oldId = addedDeveloper.getId();
        List<Skill> oldSkills = this.developerService.getDevSkills(addedDeveloper.getId());
        Speciality oldSpeciality = developer.getSpeciality();

        Assert.assertEquals(oldFirstName, addedDeveloper.getFirstName());
        developerService.updateDevFirstName(newFirstName, addedDeveloper.getId());

        Assert.assertNotNull(addedDeveloper.getId());

        Assert.assertEquals(oldId, developer.getId());
        Assert.assertEquals(newFirstName, this.developerService.getDevById(developer.getId()).getFirstName());
        Assert.assertEquals(oldSpeciality, this.developerService.getDevById(developer.getId()).getSpeciality());
        Assert.assertEquals(oldSkills, this.developerService.getDevSkills(addedDeveloper.getId()));
        Assert.assertEquals(oldLastName, this.developerService.getDevById(developer.getId()).getLastName());
        Assert.assertEquals(Status.ACTIVE, addedDeveloper.getStatus());

        developerService.delete(addedDeveloper.getId());
    }

    @Test
    public void testUpdateDevLastName() {
        Developer developer = new Developer();

        String oldFirstName = "First Name";
        String oldLastName = "Last Name";
        String newLastName = "New Last Name";

        developer.setFirstName(oldFirstName);
        developer.setLastName(oldLastName);

        Developer addedDeveloper = this.developerService.addNewDeveloper(developer);
        Long oldId = addedDeveloper.getId();
        List<Skill> oldSkills = this.developerService.getDevSkills(addedDeveloper.getId());
        Speciality oldSpeciality = developer.getSpeciality();

        Assert.assertEquals(oldLastName, addedDeveloper.getLastName());
        developerService.updateDevLastName(newLastName, addedDeveloper.getId());

        Assert.assertNotNull(addedDeveloper.getId());

        Assert.assertEquals(oldId, developer.getId());
        Assert.assertEquals(oldSpeciality, this.developerService.getDevById(developer.getId()).getSpeciality());
        Assert.assertEquals(oldSkills, this.developerService.getDevSkills(addedDeveloper.getId()));
        Assert.assertEquals(oldFirstName, this.developerService.getDevById(developer.getId()).getFirstName());
        Assert.assertEquals(Status.ACTIVE, addedDeveloper.getStatus());

        Assert.assertEquals(newLastName, this.developerService.getDevById(developer.getId()).getLastName());

        developerService.delete(addedDeveloper.getId());
    }

    @Test
    public void testUpdateDevSpeciality() {
        Developer developer = new Developer();
        this.specialityService = new SpecialityService();

        String oldFirstName = "First Name";
        String oldLastName = "Last Name";
        Speciality oldSpeciality = this.specialityService.getSpecById(1L);
        Speciality newSpeciality = new Speciality("New Speciality");

        developer.setFirstName(oldFirstName);
        developer.setLastName(oldLastName);

        Developer addedDeveloper = this.developerService.addNewDeveloper(developer);
        Speciality addedSpeciality = this.specialityService.addNewSpec(newSpeciality);

        Long oldId = addedDeveloper.getId();
        List<Skill> oldSkills = this.developerService.getDevSkills(addedDeveloper.getId());

        Assert.assertEquals(oldSpeciality, addedDeveloper.getSpeciality());
        developerService.updateDevSpeciality(addedSpeciality.getId(), addedDeveloper.getId());
        Assert.assertNotNull(addedDeveloper.getId());
        Assert.assertNotNull(addedSpeciality.getId());

        Assert.assertEquals(oldId, developer.getId());
        Assert.assertEquals(oldFirstName, this.developerService.getDevById(developer.getId()).getFirstName());
        Assert.assertEquals(oldSkills, this.developerService.getDevSkills(addedDeveloper.getId()));
        Assert.assertEquals(oldLastName, this.developerService.getDevById(developer.getId()).getLastName());
        Assert.assertEquals(Status.ACTIVE, addedDeveloper.getStatus());

        Assert.assertEquals(addedSpeciality, this.developerService.getDevById(developer.getId()).getSpeciality());

        developerService.delete(addedDeveloper.getId());
        specialityService.delete(addedSpeciality.getId());
    }

    @Test
    public void testDelete(){
        Developer developer = new Developer();
        developer.setFirstName("First Name");
        developer.setLastName("Last Name");

        Developer addedDeveloper = this.developerService.addNewDeveloper(developer);
        Assert.assertNotNull(this.developerService.getDevById(addedDeveloper.getId()).getId());
        this.developerService.delete(addedDeveloper.getId());
        Assert.assertNull(this.developerService.getDevById(addedDeveloper.getId()).getId());
    }
}
