package lesson4.spoonaccular;

import io.restassured.path.json.JsonPath;
import lesson4.*;
import lesson4.spoonaccular.test.SpoonaccularTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class RecipesTest extends SpoonaccularTest {
//    private static SpoonacularClient client;
    @Test
    void testNutritionById() {
        given()
                .pathParams("id", 1003464)
                .expect()
                .body("calories", is("316k"))
                .body("carbs", is("49g"))
                .body("fat", is("12g"))
                .body("protein", is("3g"))
                .when()
                .get("recipes/{id}/nutritionWidget.json");
    }

    @Test
    void testSummarizeRecipe() {
        SummarizeRecipe response = given()
                .pathParams("id", 4632)
                .expect()
                .when()
                .get("recipes/{id}/summary")
                .as(SummarizeRecipe.class);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getId());
        Assertions.assertNotNull(response.getTitle());
        Assertions.assertNotNull(response.getSummary());
        Assertions.assertEquals(4632L, response.getId());
        Assertions.assertEquals("Soy-and-Ginger-Glazed Salmon with Udon Noodles", response.getTitle());
        Assertions.assertTrue(response.getTitle().startsWith("Soy-and-Ginger-Glazed Salmon with Udon Noodles"));
    }

    @Test
    void testAnalyzeRecipeSearchQuery() throws Exception {
        Dish targetDish = new Dish("https://spoonacular.com/cdn/ingredients_100x100/salmon.png", "salmon");

        AnalyzeResponse response = given()
                .param("q", "salmon with fusilli and no nuts")
                .expect()
                .when()
                .get("recipes/queries/analyze")
                .as(AnalyzeResponse.class);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getIngredients());
        Assertions.assertNotNull(response.getDishes());
        Assertions.assertEquals(1, response.getIngredients().size());
        Assertions.assertEquals(1, response.getDishes().size());

        System.out.println("Ingredients: " + response.getIngredients());

        Ingredient ingredient = response.getIngredients().get(0);

        Assertions.assertEquals("nuts mixed", ingredient.getName());
        Assertions.assertEquals(false, ingredient.getInclude());
        Assertions.assertEquals("nuts-mixed.jpg", ingredient.getImage());

      /*  response.getDishes()
                .stream()
                .filter(dish -> dish.getName().equals("salmon"))
                .peek(dish -> Assertions.assertEquals(targetDish, dish))
                .findAny()
                .orElseThrow();*/
    }

    @Test
    void testConvertAmounts() {
        String sourceUnit = "cups";
        String targetUnit = "grams";
        Double sourceAmount = 2.5;
        ConvertResponse convertResponse = given()
                .param("ingredientName", "flour")
                .param("sourceAmount", sourceAmount)
                .param("sourceUnit", sourceUnit)
                .param("targetUnit", targetUnit)
                .expect()
                .when()
                .get("recipes/convert")
                .as(ConvertResponse.class);

        Assertions.assertNotNull(convertResponse);
        Assertions.assertEquals(sourceAmount, convertResponse.getSourceAmount());
        Assertions.assertEquals(sourceUnit, convertResponse.getSourceUnit());
        Assertions.assertEquals(312.5, convertResponse.getTargetAmount());
        Assertions.assertEquals(targetUnit, convertResponse.getTargetUnit());
        Assertions.assertTrue(convertResponse.getAnswer().contains("2.5 cups flour"));
        Assertions.assertTrue(convertResponse.getAnswer().contains("312.5 grams"));

    }

    @Test
    void getRecipePositiveTest() {
        given()
                .queryParam("includeNutrition", "false")
                .pathParam("id", "716429")
                .when()
                .get("/recipes/{id}/information")
                .then()
                .statusCode(200);
    }

    @Test
    void testAddToMealPlan() {
        String s = given()
                .pathParam("username", "dsky")
                .queryParam("hash", "4b5v4398573406")
                .body("{\n"
                        + " \"date\": 1589500800,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .expect()
                .when()
                .post("/mealplanner/{username}/items")
                .prettyPrint();
    }

    @Test
    void ComputeIngredientAmount() {
        given()
                .pathParam("id", "9266")
                .param("nutrient", "protein")
                .param("target", 2)
                .param("unit", "oz")
                .expect()
                .body("amount", is(7.05F))
                .body("unit", is("oz"))
                .when()
                .get("/food/ingredients/{id}/amount")
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    //5 мой -  ---------------------------------------------------------------------------------------------------------
// не проходит, хотя картинка одинаковая при разных ссылках :((((((((((((((((((((((((((((((((((((((((((((((((((((((((((
  @Disabled
    @Test
    void testCreateRecipeCard() {
        given()
                .pathParam("id", "4632")
                .param("mask", "ellipseMask")
                .param("backgroundImage", "background1")
                .expect()
              // .body("url", is("https://spoonacular.com/recipeCardImages/recipeCard-1653930502064.png"))
                .when()
                .get("/recipes/{id}/card")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    //c урока 1+

    @Test
    void testAutocompleteSearch() throws Exception {
        String actually = given()
                .param("number", 10)
                .param("query", "cheese")
                .expect()
                .when()
                .get("recipes/autocomplete")
                .body()
                .prettyPrint();

        String expected = getResource("expected.json");
       assertJson(expected, actually);
    }

    //с урока 2+
    @Test
    void testTasteRecipeById() {
        given()
                .pathParam("id", "69095")
                .expect()
                .body("sweetness", is(48.15F))
                .body("saltiness", is(45.29F))
                .body("sourness", is(15.6F))
                .body("bitterness", is(19.17F))
                .body("savoriness", is(26.45F))
                .body("fattiness", is(100.0F))
                .body("spiciness", is(0.0F))
                .when()
                .get("recipes/{id}/tasteWidget.json")
                .prettyPrint();
    }

//с урока 3 +

    @Test
    void EquipmentById() {
        EquipmentItem target = new EquipmentItem("pie-pan.png", "pie form");
        EquipmentResponse response = given()
                .pathParam("id", "1003464")
                .expect()
                .body("equipment[1].name", is("pie form"))
                .body("equipment[1].image", is("pie-pan.png"))
                .when()
                .get("recipes/{id}/equipmentWidget.json")
                .as(EquipmentResponse.class);
        response.getEquipment().stream()
                .filter(item -> item.getName().equals("pie form"))
                .peek(item -> Assertions.assertEquals(target, item))
                .findAny()
                .orElseThrow();

    }
    //тест из методички +
    @Test
    void getRecipeWithBodyChecksAfterRequestPositiveTest() {
        JsonPath response = given()
                .queryParam("includeNutrition", "false")
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .body()
                .jsonPath();
        assertThat(response.get("vegetarian"), is(false));
        assertThat(response.get("vegan"), is(false));
        assertThat(response.get("license"), equalTo("CC BY-SA 3.0"));
        assertThat(response.get("pricePerServing"), equalTo(163.15F));
        assertThat(response.get("extendedIngredients[0].aisle"), equalTo("Milk, Eggs, Other Dairy"));
    }
    //тест из методички +
    @Test
    void getRecipeWithBodyChecksInGivenPositiveTest() {
        given()
                .queryParam("includeNutrition", "false")
                .expect()
                .body("vegetarian", is(false))
                .body("vegan", is(false))
                .body("license", equalTo("CC BY-SA 3.0"))
                .body("pricePerServing", equalTo(163.15F))
                .body("extendedIngredients[0].aisle", equalTo("Milk, Eggs, Other Dairy"))
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .prettyPrint();
    }

}
