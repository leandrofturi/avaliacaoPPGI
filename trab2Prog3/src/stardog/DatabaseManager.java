package stardog;

import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;
import com.stardog.stark.IRI;
import com.stardog.stark.Resource;
import com.stardog.stark.Value;
import com.stardog.stark.Values;
import com.stardog.stark.query.SelectQueryResult;
import static com.stardog.stark.Values.literal;

public class DatabaseManager {
	
	private AdminConnection aAdminConnection;
	private Connection aConnection;
 
	public void createDatabase(String database) {
		AdminConnection aAdminConnection = AdminConnectionConfiguration.toEmbeddedServer()
				.credentials("admin", "admin")
                .connect();
        if (aAdminConnection.list().contains(database)) {
            aAdminConnection.drop(database);
        }

        aAdminConnection.newDatabase(database);
        this.aAdminConnection = aAdminConnection;
        this.aConnection = ConnectionConfiguration
                .to(database)
                .credentials("admin", "admin")
                .connect();
	}
                
	public void removeDatabase(String database) {
		if (this.aAdminConnection.list().contains(database)) {
            this.aAdminConnection.drop(database);
        }
		this.aAdminConnection.close();
    }
	
	public static IRI iri(String namespace, String localName) {
		return Values.iri(namespace, localName);
	}

	public void addStatment(Resource subject, IRI predicate, Value object) {
		this.aConnection.begin();
		this.aConnection.add().statement(subject, predicate, object);
		this.aConnection.commit();
	}

	public void addStatment(Resource subject, IRI predicate, String object) {
		this.aConnection.begin();
		this.aConnection.add().statement(subject, predicate, literal(object));
		this.aConnection.commit();
	}

	public void rmStatment(Resource subject, IRI predicate, Value object) {
		this.aConnection.begin();
		this.aConnection.remove().statements(subject, predicate, object);
		this.aConnection.commit();
	}

	public void rmStatment(Resource subject, IRI predicate, String object) {
		this.aConnection.begin();
		this.aConnection.remove().statements(subject, predicate, literal(object));
		this.aConnection.commit();
	}

	public void query(String query) {
		SelectQueryResult result = this.aConnection
				.select(query + "limit 10").
				execute();

		System.out.println("The first ten results...");

		while (result.hasNext()) {
			System.out.println(result.next());
		}
	}
	
	/*
	String NS = "http://example.com/";
    IRI IronMan = StardogData.iri(NS, "ironMan");
    IRI BlackWidow = StardogData.iri(NS, "blackWidow");
    IRI CaptainAmerica = StardogData.iri(NS, "captainAmerica");
    IRI Thor = StardogData.iri(NS, "thor");
    IRI IncredibleHulk = StardogData.iri(NS, "incredibleHulk");

    SD.addStatment(IronMan, RDF.TYPE, FOAF.Person);
    SD.addStatment(IronMan, FOAF.name, "Anthony Edward Stark");
    SD.addStatment(IronMan, FOAF.title, "Iron Man");
    SD.addStatment(IronMan, FOAF.givenName, "Anthony");
    SD.addStatment(IronMan, FOAF.familyName, "Stark");
    SD.addStatment(IronMan, FOAF.knows, BlackWidow);
    SD.addStatment(IronMan, FOAF.knows, CaptainAmerica);
    SD.addStatment(IronMan, FOAF.knows, Thor);
    SD.addStatment(IronMan, FOAF.knows, IncredibleHulk);

	//SD.query("SELECT * WHERE { ?s ?p  ?o .}");
	 */
}
