package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the FoodPost type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "FoodPosts")
@Index(name = "foodItem", fields = {"foodID","orderID"})
@Index(name = "orderPost", fields = {"orderID","foodID"})
public final class FoodPost implements Model {
  public static final QueryField ID = field("FoodPost", "id");
  public static final QueryField TYPE = field("FoodPost", "type");
  public static final QueryField QUANTITY = field("FoodPost", "quantity");
  public static final QueryField RESTAURANT = field("FoodPost", "foodID");
  public static final QueryField CART = field("FoodPost", "orderID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String type;
  private final @ModelField(targetType="String", isRequired = true) String quantity;
  private final @ModelField(targetType="Restaurant", isRequired = true) @BelongsTo(targetName = "foodID", type = Restaurant.class) Restaurant restaurant;
  private final @ModelField(targetType="Cart") @BelongsTo(targetName = "orderID", type = Cart.class) Cart cart;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getType() {
      return type;
  }
  
  public String getQuantity() {
      return quantity;
  }
  
  public Restaurant getRestaurant() {
      return restaurant;
  }
  
  public Cart getCart() {
      return cart;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private FoodPost(String id, String type, String quantity, Restaurant restaurant, Cart cart) {
    this.id = id;
    this.type = type;
    this.quantity = quantity;
    this.restaurant = restaurant;
    this.cart = cart;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      FoodPost foodPost = (FoodPost) obj;
      return ObjectsCompat.equals(getId(), foodPost.getId()) &&
              ObjectsCompat.equals(getType(), foodPost.getType()) &&
              ObjectsCompat.equals(getQuantity(), foodPost.getQuantity()) &&
              ObjectsCompat.equals(getRestaurant(), foodPost.getRestaurant()) &&
              ObjectsCompat.equals(getCart(), foodPost.getCart()) &&
              ObjectsCompat.equals(getCreatedAt(), foodPost.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), foodPost.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getType())
      .append(getQuantity())
      .append(getRestaurant())
      .append(getCart())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("FoodPost {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("type=" + String.valueOf(getType()) + ", ")
      .append("quantity=" + String.valueOf(getQuantity()) + ", ")
      .append("restaurant=" + String.valueOf(getRestaurant()) + ", ")
      .append("cart=" + String.valueOf(getCart()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TypeStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static FoodPost justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new FoodPost(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      type,
      quantity,
      restaurant,
      cart);
  }
  public interface TypeStep {
    QuantityStep type(String type);
  }
  

  public interface QuantityStep {
    RestaurantStep quantity(String quantity);
  }
  

  public interface RestaurantStep {
    BuildStep restaurant(Restaurant restaurant);
  }
  

  public interface BuildStep {
    FoodPost build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep cart(Cart cart);
  }
  

  public static class Builder implements TypeStep, QuantityStep, RestaurantStep, BuildStep {
    private String id;
    private String type;
    private String quantity;
    private Restaurant restaurant;
    private Cart cart;
    @Override
     public FoodPost build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new FoodPost(
          id,
          type,
          quantity,
          restaurant,
          cart);
    }
    
    @Override
     public QuantityStep type(String type) {
        Objects.requireNonNull(type);
        this.type = type;
        return this;
    }
    
    @Override
     public RestaurantStep quantity(String quantity) {
        Objects.requireNonNull(quantity);
        this.quantity = quantity;
        return this;
    }
    
    @Override
     public BuildStep restaurant(Restaurant restaurant) {
        Objects.requireNonNull(restaurant);
        this.restaurant = restaurant;
        return this;
    }
    
    @Override
     public BuildStep cart(Cart cart) {
        this.cart = cart;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String type, String quantity, Restaurant restaurant, Cart cart) {
      super.id(id);
      super.type(type)
        .quantity(quantity)
        .restaurant(restaurant)
        .cart(cart);
    }
    
    @Override
     public CopyOfBuilder type(String type) {
      return (CopyOfBuilder) super.type(type);
    }
    
    @Override
     public CopyOfBuilder quantity(String quantity) {
      return (CopyOfBuilder) super.quantity(quantity);
    }
    
    @Override
     public CopyOfBuilder restaurant(Restaurant restaurant) {
      return (CopyOfBuilder) super.restaurant(restaurant);
    }
    
    @Override
     public CopyOfBuilder cart(Cart cart) {
      return (CopyOfBuilder) super.cart(cart);
    }
  }
  
}
