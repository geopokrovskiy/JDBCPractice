import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Speciality;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.service.SkillService;
import com.geopokrovskiy.service.SpecialityService;
import com.geopokrovskiy.utils.JdbcUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SpecialityServiceTest {
    private SpecialityService specialityService;

    @Before
    public void setUp(){
        this.specialityService = new SpecialityService();
    }

    @Test
    public void testAddNewSkill(){
        String specName = "Test Spec";
        Speciality speciality = new Speciality(specName);

        String sql = "SELECT * FROM speciality";
        int numberOfRowsBefore = 0;
        int numberOfRowsAfter = 0;
        try{
            PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            numberOfRowsBefore = resultSet.getRow();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        Speciality result = this.specialityService.addNewSpec(speciality);

        try{
            PreparedStatement preparedStatement = JdbcUtils.preparedStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            numberOfRowsAfter = resultSet.getRow();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getId());
        Assert.assertEquals(Status.ACTIVE, result.getStatus());
        Assert.assertEquals(specName, result.getName());
        Assert.assertEquals(1, numberOfRowsAfter - numberOfRowsBefore);

        this.specialityService.delete(result.getId());
    }

    @Test
    public void testGetAllSpecs(){
        List<Speciality> specs = this.specialityService.getAllSpecs();
        Assert.assertNotNull(specs);
        for (Speciality speciality : specs) {
            Assert.assertEquals(speciality.getStatus(), Status.ACTIVE);
            Assert.assertNotNull(speciality.getId());
            Assert.assertNotNull(speciality.getName());
        }
    }
    @Test
    public void testGetSpecById(){
        Speciality illegalSpec = this.specialityService.getSpecById(-1L);
        Assert.assertNull(illegalSpec);

        List<Speciality> specs = this.specialityService.getAllSpecs();
        for (Speciality speciality : specs) {
            Assert.assertEquals(speciality, this.specialityService.getSpecById(speciality.getId()));
        }
    }

    @Test
    public void testUpdateSpecName(){
        String oldName = "Test Spec";
        String newName = "New Name of Test Spec";
        Speciality speciality = new Speciality(oldName);

        Speciality result = this.specialityService.addNewSpec(speciality);
        Long oldId = result.getId();
        Assert.assertEquals(oldName, result.getName());

        Speciality updated = this.specialityService.updateSpecName(newName, oldId);
        Assert.assertEquals(oldId, updated.getId());
        Assert.assertEquals(newName, this.specialityService.getSpecById(updated.getId()).getName());
        Assert.assertEquals(Status.ACTIVE, updated.getStatus());

        this.specialityService.delete(result.getId());

    }

    @Test
    public void testDeleteSpec(){
        String specName = "Test Spec";
        Speciality spec = new Speciality(specName);

        Speciality addedSpec = this.specialityService.addNewSpec(spec);
        Assert.assertNotNull(this.specialityService.getSpecById(addedSpec.getId()));
        this.specialityService.delete(addedSpec.getId());
        Assert.assertNull(this.specialityService.getSpecById(addedSpec.getId()));
    }
}
