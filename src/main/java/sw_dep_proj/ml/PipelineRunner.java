package sw_dep_proj.ml;

import weka.classifiers.trees.RandomForest;

public class PipelineRunner {

    public static void main(String args[]) {

        pipelineForProj("azure-sdk-for-java");
        pipelineForProj("zuul");
        pipelineForProj("graal");
        pipelineForProj("shopizer");
    }

    public static void pipelineForProj(String projectName){
        Pipeline pipeline = new Pipeline("src\\main\\java\\final_data\\" + projectName + "real_dataset.csv", // insert here the path of your dataset in input
                "C:\\Users\\bacco\\OneDrive\\Desktop\\progetti uni\\homework_sw_dep\\src\\main\\java\\final_data\\ml_output.csv", // insert here the path to your output csv
                new RandomForest());
    }
}
