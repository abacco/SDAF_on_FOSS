package sw_dep_proj;


import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.GitRemoteRepository;
import org.repodriller.scm.GitRepository;

// ref : https://github.com/mauricioaniche/repodriller/blob/master/manual/repodriller-2.0.0.md
public class MyStudy implements org.repodriller.Study {

    // RepoDriller needs a Study. The interface is quite simple: a single execute() method:
    public static void main(String[] args) {

        new RepoDriller().start(new MyStudy());
    }


/*    public void mineRepos(){

        Path azurePath = Paths.get("src/main/java/sw_dep_proj/tmpDirStudy/azure");
        boolean azureMined = Files.exists(azurePath);

        Path seleniumPath = Paths.get("src/main/java/sw_dep_proj/tmpDirStudy/selenium");
        boolean seleniumMined = Files.exists(seleniumPath);

        Path netflixPath = Paths.get("src/main/java/sw_dep_proj/tmpDirStudy/netflix");
        boolean netflixMined = Files.exists(netflixPath);

        Path oraclePath = Paths.get("src/main/java/sw_dep_proj/tmpDirStudy/oracle");
        boolean oracleMined = Files.exists(oraclePath);

        Path shopizerPath = Paths.get("src/main/java/sw_dep_proj/tmpDirStudy/shopizer");
        boolean shopizerMined = Files.exists(shopizerPath);*/

/*        if(!seleniumMined && !netflixMined && !azureMined && !oracleMined && !shopizerMined ){
            String seleniumRepo = "https://github.com/SeleniumHQ/selenium.git";
            buildRepo(seleniumRepo, "selenium");
            String netflixRepo = "https://github.com/Netflix/zuul.git";
            buildRepo(netflixRepo, "netflix");
            String azureRepo = "https://github.com/Azure/azure-sdk-for-java.git";
            buildRepo(azureRepo, "azure");
            String oracleRepo = "https://github.com/oracle/graal.git";
            buildRepo(oracleRepo, "oracle");
            String shopizerRepo = "https://github.com/shopizer-ecommerce/shopizer.git";
            buildRepo(shopizerRepo, "shopizer");
        }*/

/*        String shopizerRepo = "https://github.com/shopizer-ecommerce/shopizer.git";
        //buildRepo(shopizerRepo, "shopizer");
    }*/

    // configure here your study,
    // projects to analyze, metrics to be executed, and output files

    public void execute() {

        String realPath = "C:\\Users\\bacco\\OneDrive\\Desktop\\progetti uni\\shopizer";

        new RepositoryMining()
                .in(GitRepository.singleProject(realPath))
                .through(Commits.all())
                .process(new DevelopersVisitor(),
                            new CSVFile("src/main/java/sw_dep_proj/csv/shopizer.csv"))
                .mine();

/*        new RepositoryMining().
                in(GitRemoteRepository
                        .hostedOn("https://github.com/mauricioaniche/repodriller.git")
                        .inTempDir("src/main/java/sw_dep_proj/tmpDirStudy/shopizer-prova2")
                        .asBareRepos()
                        .buildAsSCMRepository())
        .through(Commits.all())
        .process(new DevelopersVisitor(), new CSVFile("src/main/java/sw_dep_proj/csv/devs.csv"))
        .mine();*/


/*        mineRepos();
        System.out.println("repo mined - retrieving commits");*/
/*            retrieveRepoCommits("selenium", "seleniumCSV");
            retrieveRepoCommits("netflix", "netflixCSV");
            retrieveRepoCommits("azure", "azureCSV");
            retrieveRepoCommits("oracle", "oracleCSV");*/
/*            retrieveRepoCommits("shopizer", "ssShopizerCSV.csv");*/
        //proof2();
    }

/*    public void buildRepo(String githubLink, String dir){
        GitRemoteRepository.hostedOn(githubLink)
                .inTempDir("src/main/java/sw_dep_proj/tmpDirStudy/" + dir)
                //.asBareRepos();
                .buildAsSCMRepository();
    }

    public void proof2(){
        Calendar fromCal = Calendar.getInstance();
        fromCal.set(2000, 1,1);

        Calendar toCal = Calendar.getInstance();
        toCal.set(2020, 1, 1);*/

/*        new RepositoryMining()
                .in(GitRepository.singleProject("C:\\Users\\bacco\\OneDrive\\Desktop\\homework sw_dep\\jpacman-framework")) // let's test this project
                //.through(Commits.all())
                .through(Commits.betweenDates(fromCal, toCal))
                .process(new DevelopersVisitor(), new CSVFile("src/main/java/sw_dep_proj/csv/proof2.csv")) // output folder
                .filters(
                        new OnlyModificationsWithFileTypes(Collections.singletonList(".java"))
                        //new OnlyInBranches(Arrays.asList("master"))
                        //new OnlyNoMerge(),
                        //new OnlyInMainBranch()
                )
                .mine();*/
    }

    // funziona
/*    public void proof(){
        Calendar fromCal = Calendar.getInstance();
        fromCal.set(2020, 1,1);

        Calendar toCal = Calendar.getInstance();
        toCal.set(2021, 1, 1);*/
/*
        new RepositoryMining()
                .in(GitRepository.singleProject("C:\\Users\\bacco\\OneDrive\\Desktop\\homework sw_dep\\jpacman-framework")) // let's test this project
                .through(Commits.all())
                //.through(Commits.betweenDates(fromCal, toCal))
                .process((CommitVisitor) new DevelopersVisitor(), new CSVFile("src/main/java/sw_dep_proj/csv/proof.csv")) // output folder
                .filters(
                        new OnlyModificationsWithFileTypes(Collections.singletonList(".java"))
                        //new OnlyInBranches(Arrays.asList("master"))
                        //new OnlyNoMerge(),
                        //new OnlyInMainBranch()
                )
                .mine();*/
/*    }*/

    // from 2020 to current year
/*    public void retrieveRepoCommits(String projectRepoOutput, String projectCsvOutput){
        Calendar fromCal = Calendar.getInstance();
        fromCal.set(2000, 1,1);

        Calendar toCal = Calendar.getInstance();
        toCal.set(2021, 1, 1);*/

/*        new RepositoryMining()
                .in(GitRepository.singleProject("C:\\Users\\bacco\\OneDrive\\Desktop\\progetti uni\\shopizer")) // let's test this project
                .through(Commits.all())
                //.through(Commits.betweenDates(fromCal, toCal))
                .filters(
                        new OnlyModificationsWithFileTypes(Collections.singletonList(".java"))
                        //new OnlyInBranches(Arrays.asList("master"))
                        //new OnlyNoMerge(),
                        //new OnlyInMainBranch()
                )
                .process(new DevelopersVisitor(), new CSVFile("src/main/java/sw_dep_proj/csv/" + projectCsvOutput)) // output folder
                .mine();*/


/*        new RepositoryMining()
                .in(GitRepository.singleProject("C:\\Users\\bacco\\OneDrive\\Desktop\\progetti uni\\SDAF_on_FOSS\\src\\main\\java\\sw_dep_proj\\tmpDirStudy\\" + projectRepoOutput)) // let's test this project
                .through(Commits.betweenDates(fromCal, toCal))
*//*                .filters(
                        new OnlyModificationsWithFileTypes(Arrays.asList(".java"))
                        //new OnlyInBranches(Arrays.asList("master"))
                        //new OnlyNoMerge(),
                        //new OnlyInMainBranch()
                )*//*
                .process(new DevelopersVisitor(), new CSVFile("src/main/java/sw_dep_proj/csv/" + projectCsvOutput)) // output folder
                .mine();*/

/*        new RepositoryMining().in(GitRemoteRepository.singleProject("https://github.com/shopizer-ecommerce/shopizer.git"))
                .through(Commits.all())
                //.through(Commits.betweenDates(fromCal, toCal))
                .filters(new OnlyModificationsWithFileTypes(Arrays.asList(".java")))
                .process(new DevelopersVisitor(), new CSVFile("src/main/java/sw_dep_proj/csv/" + projectCsvOutput)) // output folder
                .mine();*/
/*    }
}*/
