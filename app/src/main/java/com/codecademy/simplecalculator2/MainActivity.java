
// pixel2
// resolution   : 1080px * 1920px
// viewport     :  412dp *  732dp
// top          : status bar : 56 dp
// bottom       : action bar :

// top to bottom
// status bar   :  56 dp
// stack        : 320 dp
// left right   :  48 dp
// number       :  48 dp
// buttons 7-9  :  48 dp
// buttons 4-6  :  48 dp
// buttons 1-3  :  48 dp
// buttons 0.   :  48 dp

// add comment for git testing

package com.codecademy.simplecalculator2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.lang.Math;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Double.parseDouble;
import static java.math.RoundingMode.HALF_UP;

import com.codecademy.simplecalculator2.databinding.ActivityMainBinding;
import com.codecademy.simplecalculator2.databinding.ListStackTextBinding;
import com.codecademy.simplecalculator2.databinding.ButtonTextBinding;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivityTag";
    private BigDecimal stackResult, stack0, stack1;
    private Double double0, double1;
    private Boolean correctArgCount;
    private ActivityMainBinding binding;
    private ListStackTextBinding bindingStackText;
    private ButtonTextBinding bindingButtonText;
    private ListView listViewStack;
    private ArrayList<Button> memButtons = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> listStack = new ArrayList<>();
    public static ArrayList<Object> stackString = new ArrayList<>();
    public static ArrayList<Object> stackValues = new ArrayList<>();
    public static boolean leftFunction = false;
    public static boolean rightFunction = false;
    public static boolean alphaFunction = false;
    public static boolean[] memFunction = { false, false, false, false, false, false, false, false };
    public static ArrayList<Object> memRow = new ArrayList<>();
    public static ArrayList<ArrayList<Object>> memTable = new ArrayList<ArrayList<Object>>();
    private static Integer bigDecimalPrecision = 12;
    MathContext bigDecimalPrecisionMC = new MathContext(bigDecimalPrecision, HALF_UP);
    // operation with 2 arguments
    // remove operand from top of stack
    // remove operand from top of stack
    // add result to top of stack
    // add empty string to bottom of stack
    // if number available add to stack
    public void onClickAddNumbertoStack() {
        if (!binding.etNumber.getText().toString().isEmpty()) {
            try {
                stackResult = BigDecimal.valueOf(parseDouble(binding.etNumber.getText().toString()));
                stackResult = stackResult.round(bigDecimalPrecisionMC);
                stackResult.stripTrailingZeros();
                Log.d(TAG, "stackresult    : " + stackResult);
                correctArgCount = true;
                Log.d(TAG, "operands       : inputline");
                if (stackValues.size() >= 8) {
                    stackValues.remove(7);
                }
                stackValues.add(0, stackResult);
                binding.etNumber.setText("");
                // else display too few arguments popup if conversion error
            } catch (NumberFormatException e) {
                onClickTooFewArguments();
            }
        }
    }
    // prep for operations with 1 operands ; operands must be input line
    // if 1 valid operands operation can be done
    public void onClick1Input(boolean correctArgCount_arg) {
        if (!binding.etNumber.getText().toString().isEmpty()) {
            try {
                stackResult = BigDecimal.valueOf(parseDouble(binding.etNumber.getText().toString()));
                stackResult = stackResult.round(bigDecimalPrecisionMC);
                stackResult.stripTrailingZeros();
                Log.d(TAG, "stackresult    : " + stackResult);
                correctArgCount = true;
                Log.d(TAG, "operands       : inputline");
                binding.etNumber.setText("");
                stack0 = (BigDecimal) stackResult;
                stack1 = new BigDecimal(-1.0);
                double0 = stack0.doubleValue();
                double1 = new Double(-1.0);
                correctArgCount = correctArgCount_arg;
                Log.d(TAG, "operands       : stack0");
                // else display too few arguments popup if conversion error
            } catch (NumberFormatException e) {
                onClickTooFewArguments();
            }
        }
    }
    // prep for operations with 1 operands
    // if 1 valid operands operation can be done
    public void onClick1Prep(boolean correctArgCount_arg) {
        if (stackValues.get(0) instanceof BigDecimal) {
            stack0 = (BigDecimal) stackValues.get(0);
            stack1 = new BigDecimal(-1.0);
            double0 = stack0.doubleValue();
            double1 = new Double(-1.0);
            correctArgCount = correctArgCount_arg;
            Log.d(TAG, "operands       : stack0");
            // else display too few arguments popup
        } else {
            onClickTooFewArguments();
        }
    }
    // prep for operations with 2 operands
    // if 2 valid operands operation can be done
    public void onClick2Prep() {
        if ((stackValues.get(1) instanceof BigDecimal) && (stackValues.get(0) instanceof BigDecimal)) {
            stack0 = (BigDecimal) stackValues.get(0);
            stack1 = (BigDecimal) stackValues.get(1);
            double0 = stack0.doubleValue();
            double1 = stack1.doubleValue();
            correctArgCount = true;
            Log.d(TAG, "operands       : stack0 and stack1");
            // else display too few arguments popup
        } else {
            onClickTooFewArguments();
        }
    }
    // update stack operation with 1 operands
    public void onClick1Operators(BigDecimal stack0) {
        stack0 = stack0.round(bigDecimalPrecisionMC);
        stack0.stripTrailingZeros();
        Log.d(TAG, "stack0         : " + stack0);
        stackValues.remove(0);
        stackValues.add(0, stack0);
    }
    // update stack operation with 2 operands
    public void onClick2Operators(BigDecimal stack0) {
        stack0 = stack0.round(bigDecimalPrecisionMC);
        stack0.stripTrailingZeros();
        Log.d(TAG, "stack0         : " + stack0);
        stackValues.remove(0);
        stackValues.remove(0);
        stackValues.add(0, stack0);
        stackValues.add(7, getString(R.string.empty_stack));
    }
    // error message if operation requires more operands than available
    public void onClickTooFewArguments() {
        AlertDialog.Builder argCountDialogBuilder = new AlertDialog.Builder(MainActivity.this,
                R.style.CustomDialog);
        argCountDialogBuilder.setCancelable(false);
        argCountDialogBuilder.setTitle("Error:");
        argCountDialogBuilder.setMessage("Too Few Arguments");
        argCountDialogBuilder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
            @Override
                public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        // positive button response : setPositiveButton
        // negative button response : setNegativeButton
        AlertDialog dialog = argCountDialogBuilder.create();
        dialog.show();
        Button buttonNeutral = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
    }
    // error message if wrong arguments
    public void onClickWrongArguments() {
        AlertDialog.Builder argCountDialogBuilder = new AlertDialog.Builder(MainActivity.this,
                R.style.CustomDialog);
        argCountDialogBuilder.setCancelable(false);
        argCountDialogBuilder.setTitle("Error:");
        argCountDialogBuilder.setMessage("Wrong Arguments");
        argCountDialogBuilder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        // positive button response : setPositiveButton
        // negative button response : setNegativeButton
        AlertDialog dialog = argCountDialogBuilder.create();
        dialog.show();
        Button buttonNeutral = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
    }
    // display stack
    public void onClickDisplayStack() {
        createArrayList(listStack, stackValues, stackString, "stackName", "stackString");
        SimpleAdapter simpleAdapterStack = (SimpleAdapter) listViewStack.getAdapter();
        Log.d(TAG, "onClickDisplayStack");
        if (simpleAdapterStack != null) {
            simpleAdapterStack.notifyDataSetChanged();
            Log.d(TAG, "notifyDataSetChanged");
        }
    }
    private void createArrayList(
            ArrayList<HashMap<String, Object>> arrayList,
            ArrayList<Object> nameStringArray,
            ArrayList<Object> nameObjectArray,
            String nameString,
            String nameObject) {
        // if any items in list then remove
        arrayList.removeAll(arrayList);
        // loop and add data in hashmap
        // add map including data into arrayList as list item
        for (int i = 0; i < nameStringArray.size(); i++) {
            // create object of hashmap class
            HashMap<String, Object> map = new HashMap<>();
            // data entry : key : value
            Object nameStringI, nameObjectI;
            BigDecimal bigDecimalI;
            String bigDecimalS;
            nameStringI = nameStringArray.get(i);
            nameObjectI = nameObjectArray.get(i);
            if (nameStringI instanceof BigDecimal) {
                bigDecimalI = (BigDecimal) (nameStringI);
                bigDecimalI = bigDecimalI.round(bigDecimalPrecisionMC);
                bigDecimalI = bigDecimalI.stripTrailingZeros();
                if ((((BigDecimal) nameStringI).abs().compareTo(BigDecimal.valueOf(1.0e12)) == -1)
                 && (((BigDecimal) nameStringI).abs().compareTo(BigDecimal.valueOf(1.0e-1)) == +1))
                {
                    bigDecimalS = bigDecimalI.toPlainString();
                } else {
                    bigDecimalS = bigDecimalI.toString();
                }
                map.put(nameString, bigDecimalS);
                map.put(nameObject, nameObjectI);
            } else {
                map.put(nameString, nameStringI);
                map.put(nameObject, nameObjectI);
            }
            arrayList.add(0, map);
        }
    }

    @Override
    public void onClick(View view) {
        Integer opTag;
        Integer opTagI;
        Integer opButton;
        String inputLine;
        opTag = (Integer) view.getTag();
        opButton = (Integer) view.getId();
        String stackString;
        stackResult = new BigDecimal(-1.0);
        stack0 = new BigDecimal(-1.0);
        stack1 = new BigDecimal(-1.0);
        double0 = new Double(-1.0);
        double1 = new Double(-1.0);
        inputLine = "";
        correctArgCount = false;
        // Log.d(TAG,"correct    : " + correctArgCount);
        // keys with left and right functions
        // if left  is set, add 1 to opTag
        // if right is set, add 2 to opTag
        // 1/x
        // delete
        // y^x
        // sqrt
        // sto / rcl
        if (leftFunction) {
            switch (opTag) {
                case 15040:
                case 16010:
                case 16040:
                case 16050:
                case 13060:
                    opTag = opTag + 1;
                    Log.d(TAG, "update opTag : " + opTag);
                    leftFunction = false;
                    binding.btnLeft.setBackground(getDrawable(R.drawable.custom_button_left));
                    break;
            }
        }
        if (rightFunction) {
            switch (opTag) {
                case 15040:
                case 16010:
                case 16040:
                case 16050:
                case 13060:
                    opTag = opTag + 2;
                    Log.d(TAG, "update opTag : " + opTag);
                    rightFunction = false;
                    binding.btnRight.setBackground(getDrawable(R.drawable.custom_button_right));
                    break;
            }
        }
        // store number
        stackString = binding.etNumber.getText().toString();
        switch (opTag) {
            // 1/x
            // 1/x  : left  : e^x
            // 1/x  : right : ln(x)
            // del
            // del  : left  : drop
            // del  : right : clear
            // y^x  : left  : 10^x
            // y^x  : right : log(x)
            // sqrt
            // sqrt : left  : x^2
            case 15040:
            case 15041:
            case 15042:
            case 16041:
            case 16042:
            case 16050:
            case 16051:
                // step 1 : if entry string not empty
                onClickAddNumbertoStack();
                // if stack0 available operation can be done
                onClick1Prep(true);
                break;
            // subtract
            // add
            // divide
            // multiply
            // y^x
            // sqrt : right : y^(1/x)
            case 15020:
            case 15030:
            case 16020:
            case 16030:
            case 16040:
            case 16052:
                // step 1 : if entry string not empty
                onClickAddNumbertoStack();
                // if stack0 and stack1 available operation can be done
                onClick2Prep();
                break;
            // change sign
            case 15050:
                // if entry string not empty
                if (!stackString.isEmpty()) {
                    stackString = binding.etNumber.getText().toString();
                    // change EEX if available
                    if (stackString.contains("e")) {
                        stackString = stackString.replace("e",   "e-");
                        stackString = stackString.replace("e--", "e+");
                        stackString = stackString.replace("e-+", "e-");
                    } else if (stackString.contains("-")) {
                        stackString = stackString.replace("-", "+");
                    } else if (stackString.contains("+")) {
                        stackString = stackString.replace("+", "-");
                    } else {
                        stackString = "-" + stackString;
                    }
                    binding.etNumber.setText(stackString);
                // if stack0 available operation can be done
                } else {
                    if (stackValues.get(0) instanceof BigDecimal) {
                        onClick1Prep(true);
                    }
                }
                break;
            // delete
            case 16010:
                // if entry string not empty remove it
                if (!stackString.isEmpty()) {
                    binding.etNumber.setText(stackString.substring(0, stackString.length() - 1));
                // if stack0 available operation can be done
                } else {
                    if (stackValues.get(0) instanceof BigDecimal) {
                        correctArgCount = true;
                        Log.d(TAG, "operands       : stack0");
                    // else display too few arguments popup
                    } else {
                        onClickTooFewArguments();
                    }
                }
                break;
            // drop
            case 16011:
                // if entry string not empty remove it
                if (!stackString.isEmpty()) {
                    binding.etNumber.setText("");
                    // if stack0 available operation can be done
                } else {
                    if (stackValues.get(0) instanceof BigDecimal) {
                        correctArgCount = true;
                        Log.d(TAG, "operands       : stack0");
                        // else display too few arguments popup
                    } else {
                        onClickTooFewArguments();
                    }
                }
                break;
            // clear
            case 16012:
                // if entry string not empty remove it
                if (!stackString.isEmpty()) {
                    binding.etNumber.setText("");
                // while stack0 is available remove it
                } else {
                    if (stackValues.get(0) instanceof BigDecimal) {
                        for (int i = 0; i < 8; i++) {
                            if (stackValues.get(0) instanceof BigDecimal) {
                                Log.d(TAG, "operands       : " + i + stackValues.get(0));
                                stackValues.remove(0);
                                stackValues.add(7, getString(R.string.empty_stack));
                            }
                        }
                    } else {
                        onClickTooFewArguments();
                    }
                }
                break;
            // enter
            case 15010:
                // if entry string not empty
                if (!stackString.isEmpty()) {
                    if (stackString.indexOf("e", 0) ==
                        stackString.length() - 1) {
                        onClickWrongArguments();
                    } else {
                        try {
                            stackResult = BigDecimal.valueOf(parseDouble(stackString));
                            stackResult = stackResult.round(bigDecimalPrecisionMC);
                            stackResult = stackResult.stripTrailingZeros();
                            correctArgCount = true;
                            Log.d(TAG, "operands       : inputline");
                            Log.d(TAG, "stackresult    : " + stackResult);
                            // else display too few arguments popup if conversion error
                        } catch (NumberFormatException e) {
                            onClickTooFewArguments();
                        }
                    }
                // duplicate top of stack if available
                } else {
                    if (stackValues.get(0) instanceof BigDecimal) {
                        stackResult = (BigDecimal) stackValues.get(0);
                        correctArgCount = true;
                        Log.d(TAG, "operands       : stack0");
                    // else display too few arguments popup
                    } else {
                        onClickTooFewArguments();
                    }
                // convert entry string to operand if possible
                }
                break;
            // keys 0 to 9, EEX
            case 11010:
                inputLine = stackString + "0";
                binding.etNumber.setText(inputLine);
                break;
            case 11020:
            case 12020:
            case 13020:
            case 11030:
            case 12030:
            case 13030:
            case 11040:
            case 12040:
            case 13040:
                opTagI = opTag;
                opTagI = opTagI - 10000;
                switch (opTag % 100) {
                    case 20 :
                        opTagI = opTagI - 20;
                        opTagI = opTagI / 1000;
                        opTagI = opTagI + 0;
                        break;
                    case 30 :
                        opTagI = opTagI - 30;
                        opTagI = opTagI / 1000;
                        opTagI = opTagI + 3;
                        break;
                    case 40 :
                        opTagI = opTagI - 40;
                        opTagI = opTagI / 1000;
                        opTagI = opTagI + 6;
                        break;
                    default :
                        break;
                }
                inputLine = stackString + opTagI;
                binding.etNumber.setText(inputLine);
                break;
            // period
            case 12010:
                // if input line containts e cannot add .
                inputLine = stackString;
                if (inputLine.contains("e")) {
                    onClickWrongArguments();
                } else {
                    inputLine = stackString + ".";
                    binding.etNumber.setText(inputLine);
                }
                break;
            // EEX
            case 13050:
                // if input line empty add 1e
                inputLine = stackString;
                if (inputLine.length() == 0) {
                    inputLine = "1e";
                } else {
                    inputLine = stackString + "e";
                    inputLine = inputLine.replace("ee", "e");
                }
                binding.etNumber.setText(inputLine);
                break;
            // left and right are exclusive
            // either botton is turned on the other must be turned off
            // left
            case 11050 :
                if (leftFunction) {
                    leftFunction = false;
                    binding.btnLeft.setBackground(getDrawable(R.drawable.custom_button_left));
                } else {
                    leftFunction  = true;
                    rightFunction = false;
                    binding.btnLeft.setBackground(getDrawable(R.drawable.custom_button_left_pressed));
                    binding.btnRight.setBackground(getDrawable(R.drawable.custom_button_right));
                }
                break;
            // right
            case 12050 :
                if (rightFunction) {
                    rightFunction = false;
                    binding.btnRight.setBackground(getDrawable(R.drawable.custom_button_right));
                } else {
                    rightFunction = true;
                    leftFunction  = false;
                    binding.btnRight.setBackground(getDrawable(R.drawable.custom_button_right_pressed));
                    binding.btnLeft.setBackground(getDrawable(R.drawable.custom_button_left));
                }
                break;
            // alpha
            case 14050:
                if (alphaFunction) {
                    alphaFunction = false;
                    binding.btnAlpha.btnButton.setBackground(getDrawable(R.drawable.custom_button_large));
                } else {
                    alphaFunction = true;
                    binding.btnAlpha.btnButton.setBackground(getDrawable(R.drawable.custom_button_large_pressed));
                }
                break;
            // STO store memory
            case 13061:
                onClick1Input(true);
                if (double0.intValue() >= 1 && double0.intValue() <= 8) {
                    opTagI = double0.intValue() - 1;
                    Log.d(TAG, "memory : " + opTagI);
                    // store stack into memory
                    memFunction[opTagI] = true;
                    memButtons.get(opTagI).setBackground(getDrawable(R.drawable.custom_button_small_pressed));
                    memRow = (ArrayList) stackValues.clone();
                    memTable.remove(opTagI);
                    memTable.add(opTagI, memRow);
                } else {
                    onClickWrongArguments();
                }
                break;
            // RCL recall memory
            case 13062:
                onClick1Input(true);
                if (double0.intValue() >= 1 && double0.intValue() <= 8) {
                    opTagI = double0.intValue() - 1;
                    Log.d(TAG, "memory : " + opTagI);
                    // store stack into memory
                    memFunction[opTagI] = false;
                    // memButtons.get(opTagI).setBackground(getDrawable(R.drawable.custom_button_small));
                    memRow = memTable.get(opTagI);
                    stackValues = (ArrayList) memRow.clone();
                } else {
                    onClickWrongArguments();
                }
                break;
            default:
                break;
        }
        if (correctArgCount) {
            switch (opTag) {
                // enter
                case 15010:
                    if (stackValues.size() >= 8) {
                        stackValues.remove(7);
                    }
                    stackValues.add(0, stackResult);
                    binding.etNumber.setText("");
                    break;
                // subtract
                case 15020:
                    stackResult = stack1.subtract(stack0);
                    onClick2Operators(stackResult);
                    break;
                // add
                case 15030:
                    stackResult = stack1.add(stack0);
                    onClick2Operators(stackResult);
                    break;
                // 1/x
                case 15040:
                    stackResult = new BigDecimal(1.0).divide(stack0, bigDecimalPrecision, HALF_UP);
                    onClick1Operators(stackResult);
                    break;
                // e^x
                case 15041:
                    stackResult = BigDecimal.valueOf(Math.exp(double0));
                    onClick1Operators(stackResult);
                    break;
                // ln(x)
                case 15042:
                    stackResult = BigDecimal.valueOf(Math.log(double0));
                    onClick1Operators(stackResult);
                    break;
                // change sign
                case 15050:
                    stackResult = new BigDecimal(0.0).subtract(stack0);
                    onClick1Operators(stackResult);
                    break;
                // delete
                case 16010:
                    stackValues.remove(0);
                    stackValues.add(7, getString(R.string.empty_stack));
                    break;
                // drop
                case 16011:
                    stackValues.remove(0);
                    stackValues.add(7, getString(R.string.empty_stack));
                    break;
                // divide
                case 16020:
                    stackResult = stack1.divide(stack0, bigDecimalPrecision, HALF_UP);
                    onClick2Operators(stackResult);
                    break;
                // multiply
                case 16030:
                    stackResult = stack1.multiply(stack0);
                    onClick2Operators(stackResult);
                    break;
                // y^x
                case 16040:
                    stackResult = BigDecimal.valueOf(Math.exp(double0 * Math.log(double1)));
                    onClick2Operators(stackResult);
                    break;
                // 10^x
                case 16041:
                    stackResult = BigDecimal.valueOf(Math.pow(10, double0));
                    onClick1Operators(stackResult);
                    break;
                // log(x)
                case 16042:
                    stackResult = BigDecimal.valueOf(Math.log10(double0));
                    onClick1Operators(stackResult);
                    break;
                // sqrt
                case 16050:
                    stackResult = new BigDecimal(Math.sqrt(double0));
                    onClick1Operators(stackResult);
                    break;
                // x^2
                case 16051:
                    stackResult = BigDecimal.valueOf(Math.pow(double0, 2));
                    onClick1Operators(stackResult);
                    break;
                // y^(1/x)
                case 16052:
                    stackResult = BigDecimal.valueOf(Math.pow(double1, 1/double0));
                    onClick2Operators(stackResult);
                    break;
                default:
                    break;
            }
            leftFunction  = false;
            rightFunction = false;
            binding.btnLeft.setBackground(getDrawable(R.drawable.custom_button_left));
            binding.btnRight.setBackground(getDrawable(R.drawable.custom_button_right));
        }
        Log.d(TAG, "stackN         : " + stackValues.size());
        Log.d(TAG, "opTag          : " + opTag);
        Log.d(TAG, "opButton       : " + opButton);
        /*
        for (int i = 0; i < 8; i++) {
            Log.d(TAG, "stack" + i + "    : " + stackValues.get(i));
        }
        */
        // Log.d(TAG,"correct    : " + correctArgCount);
        onClickDisplayStack();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        bindingStackText = ListStackTextBinding.inflate(getLayoutInflater());
        bindingButtonText = ButtonTextBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        listViewStack = binding.stackView;
        binding.btnKey0.btnButton.setOnClickListener((MainActivity.this));
        binding.btnKey1.btnButton.setOnClickListener((MainActivity.this));
        binding.btnKey4.btnButton.setOnClickListener((MainActivity.this));
        binding.btnKey7.btnButton.setOnClickListener((MainActivity.this));
        binding.btnPeriod.btnButton.setOnClickListener((MainActivity.this));
        binding.btnKey2.btnButton.setOnClickListener((MainActivity.this));
        binding.btnKey5.btnButton.setOnClickListener((MainActivity.this));
        binding.btnKey8.btnButton.setOnClickListener((MainActivity.this));
        binding.btnSpace.btnButton.setOnClickListener((MainActivity.this));
        binding.btnKey3.btnButton.setOnClickListener((MainActivity.this));
        binding.btnKey6.btnButton.setOnClickListener((MainActivity.this));
        binding.btnKey9.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMisc1.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMisc2.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMisc3.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMisc4.btnButton.setOnClickListener((MainActivity.this));
        binding.btnAlpha.btnButton.setOnClickListener((MainActivity.this));
        binding.btnEnter.btnButton.setOnClickListener((MainActivity.this));
        binding.btnSubtract.btnButton.setOnClickListener((MainActivity.this));
        binding.btnAddition.btnButton.setOnClickListener((MainActivity.this));
        binding.btnOneDivX.btnButton.setOnClickListener((MainActivity.this));
        binding.btnChangeSign.btnButton.setOnClickListener((MainActivity.this));
        binding.btnDelete.btnButton.setOnClickListener((MainActivity.this));
        binding.btnDivision.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMultiply.btnButton.setOnClickListener((MainActivity.this));
        binding.btnYPowerX.btnButton.setOnClickListener((MainActivity.this));
        binding.btnSqrt.btnButton.setOnClickListener((MainActivity.this));
        binding.btnLeft.setOnClickListener((MainActivity.this));
        binding.btnRight.setOnClickListener((MainActivity.this));
        binding.btnEEX.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMEM.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMem1.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMem2.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMem3.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMem4.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMem5.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMem6.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMem7.btnButton.setOnClickListener((MainActivity.this));
        binding.btnMem8.btnButton.setOnClickListener((MainActivity.this));
        binding.btnKey0.btnButton.setTag(11010);
        binding.btnKey1.btnButton.setTag(11020);
        binding.btnKey4.btnButton.setTag(11030);
        binding.btnKey7.btnButton.setTag(11040);
        binding.btnPeriod.btnButton.setTag(12010);
        binding.btnKey2.btnButton.setTag(12020);
        binding.btnKey5.btnButton.setTag(12030);
        binding.btnKey8.btnButton.setTag(12040);
        binding.btnSpace.btnButton.setTag(13010);
        binding.btnKey3.btnButton.setTag(13020);
        binding.btnKey6.btnButton.setTag(13030);
        binding.btnKey9.btnButton.setTag(13040);
        binding.btnMisc1.btnButton.setTag(14010);
        binding.btnMisc2.btnButton.setTag(14020);
        binding.btnMisc3.btnButton.setTag(14030);
        binding.btnMisc4.btnButton.setTag(14040);
        binding.btnAlpha.btnButton.setTag(14050);
        binding.btnEnter.btnButton.setTag(15010);
        binding.btnSubtract.btnButton.setTag(15020);
        binding.btnAddition.btnButton.setTag(15030);
        binding.btnOneDivX.btnButton.setTag(15040);
        binding.btnChangeSign.btnButton.setTag(15050);
        binding.btnDelete.btnButton.setTag(16010);
        binding.btnDivision.btnButton.setTag(16020);
        binding.btnMultiply.btnButton.setTag(16030);
        binding.btnYPowerX.btnButton.setTag(16040);
        binding.btnSqrt.btnButton.setTag(16050);
        binding.btnLeft.setTag(11050);
        binding.btnRight.setTag(12050);
        binding.btnEEX.btnButton.setTag(13050);
        binding.btnMEM.btnButton.setTag(13060);
        binding.btnMem1.btnButton.setTag(11060);
        binding.btnMem2.btnButton.setTag(11560);
        binding.btnMem3.btnButton.setTag(12060);
        binding.btnMem4.btnButton.setTag(12560);
        binding.btnMem5.btnButton.setTag(11065);
        binding.btnMem6.btnButton.setTag(11565);
        binding.btnMem7.btnButton.setTag(12065);
        binding.btnMem8.btnButton.setTag(12565);
        for (int i = 1; i <= 8; i++) {
            stackValues.add(getString(R.string.empty_stack));
            stackString.add(i + ":");
        }
        for (int i = 1; i <= 8; i++) {
            memRow.add(getString(R.string.empty_stack));
        }
        for (int i = 1; i <= 8; i++) {
            memTable.add(memRow);
        }
        onClickDisplayStack();
        // hashmap : key = string : value : object
        // private ArrayList<HashMap<String, Object>> listStack = new ArrayList<>();
        // second paramter of the simpleadapter
        createArrayList(listStack, stackValues, stackString, "stackName", "stackString");
        // create string type array (from) which contains
        // column names for each view in each row of the list
        // forth paramter of the simpleadapter
        String[] fromArrayNamesStack = { "stackName", "stackString"};
        // create int type array (to) which constrains
        // ids for each view in each row of the list
        // fifth paramter of the simpleadapter
        int toArraysIdsStack[] = { bindingStackText.textViewRight.getId(), bindingStackText.textViewLeft.getId() };
        // create and object of simpleaapter class with required parameters
        SimpleAdapter simpleAdapterStack = new SimpleAdapter(
                getApplicationContext(),
                listStack,
                R.layout.list_stack_text,
                fromArrayNamesStack,
                toArraysIdsStack
        );
        // set adapter to listview
        listViewStack.setAdapter(simpleAdapterStack);
        // simpleAdapterStack.notifyDataSetChanged();
        // store array of memory buttons
        memButtons.add(binding.btnMem1.btnButton);
        memButtons.add(binding.btnMem2.btnButton);
        memButtons.add(binding.btnMem3.btnButton);
        memButtons.add(binding.btnMem4.btnButton);
        memButtons.add(binding.btnMem5.btnButton);
        memButtons.add(binding.btnMem6.btnButton);
        memButtons.add(binding.btnMem7.btnButton);
        memButtons.add(binding.btnMem8.btnButton);
    }
}
