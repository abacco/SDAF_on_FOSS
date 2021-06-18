package sw_dep_proj;

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRepository;

import java.util.Calendar;

// ref : https://github.com/mauricioaniche/repodriller/blob/master/manual/repodriller-2.0.0.md
public class MyStudy implements org.repodriller.Study {

    // RepoDriller needs a Study. The interface is quite simple: a single execute() method:
    public static void main(String[] args) {

        new RepoDriller().start(new MyStudy());
    }

    public void execute() {
        try {

            retrieveDataThread("shopizer", "shopizer");
            retrieveDataThread("azure-sdk-for-java", "azure");
            retrieveDataThread("zuul", "netflix");
            retrieveDataThread("graal", "graal");
            retrieveDataThread("selenium","selenium");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void retrieveDataThread(String projectName, final String outputFile) throws InterruptedException {

        final String realPath = "C:\\Users\\bacco\\OneDrive\\Desktop\\progetti uni\\" + projectName;

        final Calendar fromCal = Calendar.getInstance();
        fromCal.set(2021, 1, 1);

        final Calendar toCal = Calendar.getInstance();
        toCal.set(2021, 6, 1);

        Thread azureThread = new Thread() {
            public void run() {
                new RepositoryMining()
                        .in(GitRepository.singleProject(realPath))
                        .through(Commits.betweenDates(fromCal, toCal))
                        .process(new DevelopersVisitor(),
                                new CSVFile("src/main/java/sw_dep_proj/csv/" + outputFile.concat("Dataset.csv")))
                        .mine();
            }
        };

        azureThread.start();
    }

}
