package recognition02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;


public class Recognition02_main {
	public static void main(String[] args){

		VisualRecognition service = new VisualRecognition("2018-03-19");
		service.setApiKey("3e87906cfed8e336b0a33d10c5fd73e020234fa9");

		InputStream imagesStream = null;
		MySQL mysql = new MySQL();
		try {
			imagesStream = new FileInputStream("img/fruitbowl.jpg");
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
		  .imagesFile(imagesStream)
		  .imagesFilename("fruitbowl.jpg")
		  .threshold((float) 0.6)
		  .owners(Arrays.asList("IBM"))
		  .build();
		ClassifiedImages result = service.classify(classifyOptions).execute();
		System.out.println(result);

		String s = String.valueOf(result);

		ObjectMapper mapper = new ObjectMapper();

		try {
			JsonNode node = mapper.readTree(s);
			String name = node.get("images").get(0).get("classifiers").get(0).get("name").asText();
			 System.out.println("name : " + name);

			 String classifier_id  = node.get("images").get(0).get("classifiers").get(0).get("classifier_id").asText();
			 System.out.println("classifier_id :"+ classifier_id);

			 String classifier_class1 = node.get("images").get(0).get("classifiers").get(0).get("classes").get(0).get("class").asText();
			 System.out.println("classifier_class1 : " + classifier_class1);

			 double classifier_score1 = node.get("images").get(0).get("classifiers").get(0).get("classes").get(0).get("score").floatValue();
			 System.out.println("classifier_score1 : " + classifier_score1);

			 String classifier_class2 = node.get("images").get(0).get("classifiers").get(0).get("classes").get(1).get("class").asText();
			 System.out.println("classifier_class2 : " + classifier_class2);

			 double classifier_score2 = node.get("images").get(0).get("classifiers").get(0).get("classes").get(1).get("score").floatValue();
			 System.out.println("classifier_score2 : " + classifier_score2);

			 String classifier_class3 = node.get("images").get(0).get("classifiers").get(0).get("classes").get(2).get("class").asText();
			 System.out.println("classifier_class3 : " + classifier_class3);

			 double classifier_score3 = node.get("images").get(0).get("classifiers").get(0).get("classes").get(2).get("score").floatValue();
			 System.out.println("classifier_score3 : " + classifier_score3);



			 mysql.updateImage(classifier_class1, classifier_score1, classifier_class2, classifier_score2, classifier_class3,classifier_score3);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
