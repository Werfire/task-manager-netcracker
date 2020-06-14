import models.MutableTask;
import net.RestResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JsonIO;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.*;

public class IntegrationTest extends JerseyTest {

    Map<UUID, MutableTask> journal = JsonIO.readTasks();

    public IntegrationTest() throws IOException {
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(RestResource.class);
    }

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void TestGetRequest() {
//        TasksModel content = new TasksModel();
        HashMap<UUID, MutableTask> content;
        try (Response response = target("api/tasks").request()
                .get()) {

            assertEquals("HTTP response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
            assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

//            content = response.readEntity(HashMap.class);
            content = response.readEntity(new GenericType<HashMap<UUID, MutableTask>>() { });
            assertEquals("Content of response is: ", journal.toString(), content.toString());
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
            fail("FAIL");
        }
    }

    @Test
    public void TestPostRequest() {
        MutableTask template = new MutableTask();
        template.setStatusId("In process");
        template.setDueDate("2020 30.05 16:10");
        template.setCreationDate("2020 22.05 16:10");
        template.setName("TEST");
        template.setDescription("Testing POST request");
        JSONObject template2 = new JSONObject();
        template2.put("name", "TEST");
        template2.put("description", "Testing POST request");
        template2.put("creationDate", "2020 22.05 16:10");
        template2.put("dueDate","2020 30.05 16:10");
        template2.put("statusId", "In process");

        Entity<MutableTask> entity = Entity.json(template);
        Response response = target("api/tasks").request(MediaType.APPLICATION_JSON)
         .post(Entity.json(template2));
        assertEquals("Http Response should be 201 ", Response.Status.CREATED.getStatusCode(), response.getStatus());

        Response response2 = target("api/tasks").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(template, MediaType.APPLICATION_JSON_TYPE));
        assertEquals("Http Response should be 400 ", 400, response2.getStatus());
    }

    @Test
    public void TestPutRequest() {
        Collection<MutableTask> tasks = journal.values();
        Optional<MutableTask> mutableTask = tasks.stream().findFirst();
        if (mutableTask.isPresent()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy dd.MM HH:mm");
        JSONObject template2 = new JSONObject();
            template2.put("id",  mutableTask.get().getId());
            template2.put("name", mutableTask.get().getName());
            template2.put("description",  mutableTask.get().getDescription());
            template2.put("creationDate",  mutableTask.get().getCreationDate().format(formatter));
            template2.put("dueDate", mutableTask.get().getDueDate().format(formatter));
            template2.put("authorId",  mutableTask.get().getAuthorId());
            template2.put("statusId",  mutableTask.get().getStatusId());
        Response response = target("api/tasks/"+ mutableTask.get().getId().toString()).request()
                .put(Entity.json(template2));
        assertEquals("Http response should be 204", Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void TestDeleteRequest(){
            Collection<MutableTask> tasks = journal.values();
            Optional<MutableTask> mutableTask = tasks.stream().findFirst();

            if (mutableTask.isPresent()) {
                Response response = target("api/tasks/" + mutableTask.get().getId().toString()).request()
                        .delete();
                assertEquals("Http response should be 200", Response.Status.OK.getStatusCode(), response.getStatus());
            }
        }
}
