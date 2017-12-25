package com.scheffer.erik.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private float quantity;
    private String measure;
    private String ingredient;

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    public Ingredient() {}

    private Ingredient(Parcel in) {
        this.quantity = in.readFloat();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {return new Ingredient(source);}

        @Override
        public Ingredient[] newArray(int size) {return new Ingredient[size];}
    };
}
