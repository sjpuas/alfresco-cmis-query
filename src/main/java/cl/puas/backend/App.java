package cl.puas.backend;

import cl.puas.backend.alfresco.cmis.AlfrescoCmisHelper;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // Search all documents that contains pdf tag
        String query = "SELECT d.* from cmis:document AS d where CONTAINS ('TAG:pdf')";

        Session alfrescoSession = AlfrescoCmisHelper.getInstance().getSession(0);
        ItemIterable<QueryResult> results = AlfrescoCmisHelper.getInstance().query(alfrescoSession, query);
        for (QueryResult result : results) {
            System.out.println("Downloading file...");
            String output = "/tmp/" + result.getPropertyValueById("cmis:name");
            String objectId = result.getPropertyValueById("cmis:objectId");
            AlfrescoCmisHelper.getInstance().downloadDocument(alfrescoSession, objectId, output);
            System.out.println("File downloaded in " + output);
        }
    }
}
