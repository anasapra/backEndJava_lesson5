package lesson4.spoonaccular;

import io.restassured.path.json.JsonPath;
import lesson4.EquipmentItem;
import lesson4.EquipmentResponse;
import lesson4.spoonaccular.test.SpoonaccularTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class RecipesTest extends SpoonaccularTest {

    // все еще не получается авторизоваться через юзернейм и хеш
    @Test
    void testGetShoppingList() {
        JsonPath response  = given()
                .pathParam("username", "dsky")
                .queryParam("hash", "4b5v4398573406")
                .expect()
                .body("aisle", is("Baking"))
                .when()
                .post("/mealplanner/{username}/shopping-list")
                .jsonPath();
        assertThat(response.get("pantryItem"), is(false));;
    }

    @Test
    void testAddToShoppingList() {
        JsonPath response  = given()
                .pathParam("username", "dsky")
               .queryParam("hash", "4b5v4398573406")
                .body("{\n"
                        + " \"item\": \"1 package baking powder\",\n"
                        + " \"aisle\": \"Baking\",\n"
                        + " \"parse\": true\n"
                        + "}")
                .expect()
                .log()
                .all()
                .when()
                .post("/mealplanner/{username}/shopping-list/items")
                .jsonPath();
        assertThat(response.get("pantryItem"), is(false));;
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
                .queryParam("username", "dsky")
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
                .post("/mealplanner/dsky/items")
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
    @Test
    void testCreateRecipeCard() {
        given()
                .pathParam("id", "4632")
                .param("mask", "ellipseMask")
                .param("backgroundImage", "background1")
                .expect()
                .body("url", is("https://spoonacular.com/recipeCardImages/recipeCard-1653930502064.png"))
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
                .get("/recipes/autocomplete")
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
