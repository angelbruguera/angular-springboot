
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.dispose.Application;
import com.dispose.model.Skill;
import com.dispose.model.User;
import com.dispose.repository.SkillRepository;
import com.dispose.repository.UserRepository;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@DataJpaTest
@Transactional
public class JsonTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SkillRepository skillRepository;
	
	private Writer writer;
	
	private Gson gson;
	
	private User user;
	
	private Skill skill;
	
	
	@Before
	public void configure() throws IOException {
		this.writer = new FileWriter("./generatedJson/users.json", true);
		this.gson = new Gson();
	}
	
	@Test
	public void CreateSkills() throws Exception {
		
		for (int i = 0; i < 1000; i++) {
			this.setSkill(new Skill("python"+i, "Python", false));
			entityManager.persist(this.getSkill());
			entityManager.flush();
		}
		
		this.setSkill(skillRepository.findBySkillId("python40").get());
		assertThat(this.getSkill().getSkillId(), is("python40"));
		entityManager.clear();
	}
	
	@Test
	public void CreateUsers() throws Exception {
		
		for (int i = 0; i < 1000; i++) {
			this.setUser(new User("Angel Bruguera","brugui"+i,"as"+i+"@gmail.com","1230492"));
			entityManager.persist(this.getUser());
			entityManager.flush();
		}
		
		this.setUser(userRepository.findByUsername("brugui15").get());
		assertThat(this.getUser().getUsername(), is("brugui15"));
		entityManager.clear();
	}
	
	@Test
	public void BuildJsonFile() throws Exception {
		
		for (int i = 0; i < 100; i++) {
			
			this.writer.write(this.gson.toJson(new User("Angel Bruguera","brugui"+i,"as"+i+"@gmail.com","1230492"))+"\n");
		}
		
	}
	
	@After
	public void close() throws IOException {
		this.writer.close();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
}
