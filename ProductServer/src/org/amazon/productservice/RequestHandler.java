package org.amazon.productservice;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONException;

@Path("/requesthandler")
public class RequestHandler
{

	@Path("{f}")
	@GET
	@Produces("application/json")
	public Response convertFtoCfromInput(
			@PathParam("f") String productIdentifier) throws JSONException
	{

		Cassandra casObj = Cassandra.getInstance();
		String productInfo = casObj.selectProduct(productIdentifier);
		casObj.close();
		System.out.println("Response: " + productInfo);

		if (!productInfo.equalsIgnoreCase("None found"))
		{
			// Parse the info
			String[] resArr = productInfo.split("--");
			String productName = resArr[0];
			String[] percentArr = resArr[1].split("->");
			double posPercentage = Double.parseDouble(percentArr[0]);
			String[] tagArr = percentArr[1].trim().split("\\|\\|");
			ArrayList<String> tag = new ArrayList<String>();
			ArrayList<Integer> count = new ArrayList<Integer>();
			for (int i = 0; i < 5 && i < tagArr.length; i++)
			{
				String[] splitArr = tagArr[i].split(":");
				tag.add(splitArr[0]);
				count.add(Integer.parseInt(splitArr[1]));
			}
			ReviewsData rd = new ReviewsData(productIdentifier, productName,
					posPercentage, tag, count);
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode jsonObj = null;
			JsonNode jsonNode = mapper.valueToTree(rd);
			if (jsonNode.isObject())
			{
				jsonObj = (ObjectNode) jsonNode;
			}

			if (null != jsonObj)
			{
				String result = jsonObj.toString();
				return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(result).build();
			}
			else
			{
				System.out.println("JsonObject null and returning none found!");
				return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("None found").build();
			}
		}
		else
		{
			System.out.println("Response is none found!");
			return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(productInfo).build();
		}
	}

}