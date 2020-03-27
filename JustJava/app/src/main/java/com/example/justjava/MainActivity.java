package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;



/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 0;

    public void submitOrder(View view)
    {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhipped = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        EditText text = (EditText) findViewById(R.id.name_field);
        String name = text.getText().toString();

        String priceMessage = createOrderSummary(name , hasWhipped , hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));
        intent.putExtra(Intent.EXTRA_SUBJECT , "Just Java orders for:  " + name);
        intent.putExtra(Intent.EXTRA_TEXT , priceMessage );
        if (intent.resolveActivity(getPackageManager())!= null)
        {
            startActivity(intent);
        }

        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity , int pricePerCup , boolean WhippedCream , boolean chocolate) {
        int price = quantity * pricePerCup;
        if (WhippedCream == true)
            price = price + 10 ;
        if (chocolate == true)
            price = price + 10 ;
        return price;
    }

    public String createOrderSummary (String name , boolean hasWhipped , boolean chocolate)
    {
        int price = calculatePrice(quantity , 10 , hasWhipped , chocolate);
        String priceMessage = "Name: " + name + "\n Quantity: " + quantity;
        if (hasWhipped == true)
            priceMessage = priceMessage + "\n Whipped Cream Added";
        if (chocolate == true)
            priceMessage = priceMessage + "\n Chocolate Added" ;
        priceMessage = priceMessage +  "\n Total Amount: $" + price;
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    public void increment(View view)
    {
        //int quantity = 2 ;
       // display( quantity );
        quantity = quantity + 1;
        display( quantity );
    }

    public void decrement(View view)
    {
        if (quantity > 0)
        quantity = quantity - 1 ;
        display( quantity );
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
