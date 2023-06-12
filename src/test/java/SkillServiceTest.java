import com.geopokrovskiy.model.Skill;
import com.geopokrovskiy.model.Status;
import com.geopokrovskiy.service.SkillService;
import com.geopokrovskiy.utils.JdbcUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SkillServiceTest {
    private SkillService skillService;

    @Before
    public void setUp(){
        this.skillService = new SkillService();
    }

    @Test
    public void testAddNewSkill(){
        String skillName = "Test Skill";
        Skill skill = new Skill(skillName);

        String sql = "SELECT * FROM skills";
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

        Skill result = this.skillService.addNewSkill(skill);

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
        Assert.assertEquals(skillName, result.getName());
        Assert.assertEquals(1, numberOfRowsAfter - numberOfRowsBefore);

        this.skillService.delete(result.getId());
    }

    @Test
    public void testGetAllSkills(){
        List<Skill> skills = this.skillService.getAllSkills();
        Assert.assertNotNull(skills);
        for (Skill skill : skills) {
            Assert.assertEquals(skill.getStatus(), Status.ACTIVE);
            Assert.assertNotNull(skill.getId());
            Assert.assertNotNull(skill.getName());
        }
    }
    @Test
    public void testGetSkillById(){
        Skill illegalSkill = this.skillService.getSkillById(-1L);
        Assert.assertNull(illegalSkill);

        List<Skill> skills = this.skillService.getAllSkills();
        for (Skill skill : skills) {
            Assert.assertEquals(skill, this.skillService.getSkillById(skill.getId()));
        }
    }

    @Test
    public void testUpdateSkillName(){
        String oldName = "Test Skill";
        String newName = "New Name of Test Skill";
        Skill skill = new Skill(oldName);

        Skill result = this.skillService.addNewSkill(skill);
        Long oldId = result.getId();
        Assert.assertEquals(oldName, result.getName());

        Skill updated = this.skillService.updateSkillName(newName, oldId);
        Assert.assertEquals(oldId, updated.getId());
        Assert.assertEquals(newName, this.skillService.getSkillById(updated.getId()).getName());
        Assert.assertEquals(Status.ACTIVE, updated.getStatus());

        this.skillService.delete(result.getId());

    }

    @Test
    public void testDeleteSkill(){
        String skillName = "Test Skill";
        Skill skill = new Skill(skillName);

        Skill addedSkill = this.skillService.addNewSkill(skill);
        Assert.assertNotNull(this.skillService.getSkillById(addedSkill.getId()));
        this.skillService.delete(addedSkill.getId());
        Assert.assertNull(this.skillService.getSkillById(addedSkill.getId()));

    }
}
