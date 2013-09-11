package cl.puas.backend.alfresco.cmis;

import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.client.util.FileUtils;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bennu Ltda.
 * User: Sergio Puas
 * Date: 11-09-13
 */
public class AlfrescoCmisHelper {

    private static final AlfrescoCmisHelper instance = new AlfrescoCmisHelper();
    private SessionFactory sessionFactory;
    private Map<String, String> params;

    public static AlfrescoCmisHelper getInstance() {
        return instance;
    }

    private AlfrescoCmisHelper() {
        init();
    }

    private void init() {
        params = new HashMap<String, String>();

        // user credentials
        params.put(SessionParameter.USER, "admin");
        params.put(SessionParameter.PASSWORD, "admin");

        // connection settings
        params.put(SessionParameter.ATOMPUB_URL, "http://localhost:8080/alfresco/cmisatom");
        params.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

        // set the alfresco object factory
        params.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");
        this.sessionFactory = SessionFactoryImpl.newInstance();
    }


    public List<Repository> getAllRepositories() {
        return this.sessionFactory.getRepositories(this.params);
    }

    public Session getSession(int repoIndex) {
        return getAllRepositories().get(repoIndex).createSession();
    }

    public Session getSession(Repository repository) {
        return repository.createSession();
    }

    // The CMIS standard defines a query language based on the SQL-92 SELECT statement
    public ItemIterable<QueryResult> query(Session session, String query) {
        return session.query(query, false);
    }

    public void downloadDocument(Session session, String objectId, String output) {
        try {
            FileUtils.download(objectId, output, session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
