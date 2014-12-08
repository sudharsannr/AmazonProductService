package org.amazon.productservice;

import java.io.File;
import java.io.IOException;
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
			String[] tagArr = percentArr[1].split("\\|\\|");
			ArrayList<String> tag = new ArrayList<String>();
			ArrayList<String> count = new ArrayList<String>();
			for (int i = 0; i < 5; i++)
			{
				String[] splitArr = tagArr[i].split(":");
				tag.add(splitArr[0]);
				count.add(splitArr[1]);
			}
			ReviewsData rd = new ReviewsData(productIdentifier, productName,
					posPercentage, tag, count);
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode jsonObj = null;
			try
			{
				mapper.writerWithDefaultPrettyPrinter().writeValue(
						new File("user-modified.json"), rd);
				JsonNode jsonNode = mapper.valueToTree(rd);
				if (jsonNode.isObject())
				{
					jsonObj = (ObjectNode) jsonNode;
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			if (null != jsonObj)
			{
				String result = jsonObj.toString();
				return Response.status(200).entity(result).build();
			}
			else
			{
				return Response.status(200).entity("None found").build();
			}
		}
		else
			return Response.status(200).entity(productInfo).build();
	}

}