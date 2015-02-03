package course.labs.permissionsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.EditText;

public class ConverterActivity extends Activity {

    private static final String TAG = "Lab-Converter";
    private static final int FREEZING_POINT_FAHRENHEIT = 32;   // A Constant temperature used for the conversion between Fahrenheit
                                                              // because water freezes at this temperature (in Fahrenheit).

    private static final int CELSIUS_RISING_SCALE = 100;        // Constant of the temperature rising scale for Celsius
    private static final float FAHRENHEIT_RISING_SCALE = 180;        // Constant of the temperature rising scale for Celsius

    private static final String CELSIUS_STATEMENT_CONST = " Celsius is ";        // Constant of the Celsius connective statement of the conversion result
    private static final String FAHRENHEIT_STATEMENT_CONST = " Fahrenheit.";        // Constant of the Celsius connective statement of the conversion result

    private static final float CONVERT_DECIMAL_POINTS = 10.0f;        // Constant used as a estimation unit involved by Multiplying and dividing a Floating-value
                                                                      // to express it in 1 decimal place.

    // Variables for reference to the UI Controls in the Activity
    private RadioGroup mRadioGroup_Choice = null;
    private TextView mTextView_Result = null;
    private EditText mEditText_Temperature = null;
    private Button mBtn_Convert = null;
    private Button mBtn_Clear = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter_activity);

        // Reference the UI Controls from the Layout
        mTextView_Result = (TextView) findViewById(R.id.tv_Result);
        mEditText_Temperature = (EditText)findViewById(R.id.editText_InputTemperature);
        mBtn_Convert = (Button)findViewById(R.id.btn_Convert);
        mBtn_Clear = (Button)findViewById(R.id.btn_Clear);
        mRadioGroup_Choice = (RadioGroup)findViewById(R.id.radioGroup_ConversionChoice);

        //Setting the OnClickListener for the Buttons
        mBtn_Convert.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                String inputString = mEditText_Temperature.getText().toString();        // Variable to store the input of the EditText

                float convertedTemperature = 0.0f;        // Variable to store the converted temperature (either from Celsius or Fahrenheit)
                boolean isInput_Fahrenheit = false;        // Variable used for keep track whether the input of a temperature is in Fahrenheit

                if( ! (inputString.isEmpty()) )
                {
                    // Perform the temperature conversion when user input is not empty
                    float in_Temperature = Float.parseFloat(inputString);

                    if(mRadioGroup_Choice.getCheckedRadioButtonId() == R.id.radioBtn_ToFahrenheit)
                    {
                        //Process the conversion to Fahrenheit
                        convertedTemperature = convertToFahrenheit(in_Temperature);
                    }
                    else if(mRadioGroup_Choice.getCheckedRadioButtonId() == R.id.radioBtn_ToCelsius)
                    {
                        //Process the conversion to Celsius
                        isInput_Fahrenheit = true;
                        convertedTemperature = convertToCelsius(in_Temperature);
                    }

                    // Give the converted temperature a closest estimate value.
                    convertedTemperature = Math.round(convertedTemperature * CONVERT_DECIMAL_POINTS) / CONVERT_DECIMAL_POINTS;

                    // Finally, display the result of the conversion
                    displayResult(inputString, convertedTemperature, isInput_Fahrenheit);
                }
        }
      }); // End of Convert button setOnClickListener function

      mBtn_Clear.setOnClickListener(new OnClickListener(){

          @Override
          public void onClick(View v)
          {

              mEditText_Temperature.setText("");        // Clear the user's input from EditText.
              mRadioGroup_Choice.check(R.id.radioBtn_ToFahrenheit);        // Assign "To Fahrenheit" Radio Button to be in Checked state.

          }

      }); // End of Clear button setOnClickListener function

    }

    // TODO - Implement the temperature conversion logic and other behavior here

    private float convertToFahrenheit(float in_Celsius)
    {
        // Convert Celsius to Fahrenheit = (Celsius X 180/100) + 32
        float toFahrenheit = in_Celsius * (FAHRENHEIT_RISING_SCALE / CELSIUS_RISING_SCALE );
        toFahrenheit+= FREEZING_POINT_FAHRENHEIT;

        return toFahrenheit;        // Returns the temperature value after conversion

    }

    private float convertToCelsius(float in_Fahrenheit)
    {
        // Convert Fahrenheit to Celsius = (Fahrenheit - 32) X 100/180
        float toCelsius = in_Fahrenheit - FREEZING_POINT_FAHRENHEIT;
        toCelsius *= (CELSIUS_RISING_SCALE / FAHRENHEIT_RISING_SCALE );

        return toCelsius;        // Returns the temperature value after conversion
    }

    // Function for displaying the conversion result
    private void displayResult(String inputTemperature, float convertedTemperature, boolean isInput_Fahrenheit)
    {
        // If user inputs the temperature in Celsius, include the converted temperature (Fahrenheit)
        // at the back of the output string
        mTextView_Result.setText(inputTemperature + CELSIUS_STATEMENT_CONST + convertedTemperature + FAHRENHEIT_STATEMENT_CONST);

        // Else, include the converted temperature (Celsius) in front of the output string
        if(isInput_Fahrenheit)
        {

            mTextView_Result.setText(convertedTemperature + CELSIUS_STATEMENT_CONST + inputTemperature + FAHRENHEIT_STATEMENT_CONST);
        }
    }

}
