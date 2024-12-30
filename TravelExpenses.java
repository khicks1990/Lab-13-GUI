import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
   This program demonstrates a solution to the
   Travel Expenses programming challenge.
*/

public class TravelExpenses extends JFrame
{
   private JPanel panel;            // A panel to hold labels and text fields
   private JPanel buttonPanel;      // A panel for the buttons

   private JTextField numDays;      // Number of days
   private JTextField airfare;      // Airfare
   private JTextField carRental;    // Car rental
   private JTextField milesDriven;  // Miles driven
   private JTextField parkingFees;  // Parking fees
   private JTextField taxiCharges;  // Taxi charges
   private JTextField confReg;      // Conference registration
   private JTextField lodging;      // Per-night lodging

   private JButton calcButton;      // Calculates everything
   private JButton resetButton;     // Resets everything

   // Constants
   private final double DAILY_MEAL_RATE = 37.0;
   private final double MAX_PARKING_FEES = 10.0;
   private final double MAX_TAXI = 20.0;
   private final double MAX_LODGING_PER_NIGHT = 95.0;
   private final double MILEAGE_RATE = 0.27;

   /**
      Constructor
   */

   public TravelExpenses()
   {
      // Set the title.
      setTitle("Travel Expenses");

      // Specify what happens when the close button is clicked.
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Build the panel that contains the labels and text fields.
      buildPanel();

      // Build the panel that contains the buttons.
      buildButtonPanel();

      // Add the panel to the content pane.
      add(panel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);

      // Pack and display the window.
      pack();
      setVisible(true);
   }

   /**
      The buildPanel method creates a panel containing
      other components.
   */

   private void buildPanel()
   {

   }

   /**
      The buildbuttonPanel method creates a panel containing
      buttons.
   */

   private void buildButtonPanel()
   {
  
   }

   /**
      CalcButtonListener is an action listener class for the
      calcbutton component.
   */

   private class CalcButtonListener implements ActionListener
   {
      double days;                  // Number of days
      double air;                   // Airfare
      double carRentalActual;       // Amount spent on car rental
      double miles;                 // Mileage
      double mealsRe;               // Meals, reimbursed
      double parkingActual;         // Amount spent on parking
      double parkingRe;             // Parking, reimbursed
      double taxiActual;            // Amount spent on taxis
      double taxiRe;                // Taxi, reimbursed
      double lodgingPerNightActual; // Amount spend on lodging per night
      double lodgingRe;             // Lodging, reimbursed
      double reg;                   // Conference registration

      /**
         actionPerformed method
         @param e An ActionEvent object.
      */

      public void actionPerformed(ActionEvent e)
      {
         double actualExpenses;     // Actual expenses incurred
         double reimbursable;       // Reimbursable expenses
         double savings;            // Amount saved, if any
         double excess;             // Excess amount spent, if any
         String msg;                // Output message

         // Get the data entered.
         getData();

         // Determine the actual expenses.
         actualExpenses = determineActualExpenses();

         // Determine the reimbursement amounts.
         reimbursable = determineReimbursement();

         msg = String.format("Total expenses: $%,.2f\n" +
                             "Allowable expenses: $%,.2f\n",
                             actualExpenses, reimbursable);

         if (actualExpenses < reimbursable)
         {
            savings = reimbursable - actualExpenses;
            msg = msg + String.format("Amount saved: $%,.2f",
                                      savings);
         }
         else if (actualExpenses > reimbursable)
         {
            excess = actualExpenses - reimbursable;
            msg = msg + String.format("Amount to be paid back: $%,.2f",
                                      excess);
         }

         // Display the results.
         JOptionPane.showMessageDialog(null, msg);
      }

      /**
         The getData method gets the data entered by the user.
      */

      private void getData()
      {
         // Get the number of days.
         days = Integer.parseInt(numDays.getText());
         // Get the airfare.
         air = Double.parseDouble(airfare.getText());
         // Get the amount spent on car rental.
         carRentalActual = Double.parseDouble(carRental.getText());
         // Get the miles driven in a private auto.
         miles = Double.parseDouble(milesDriven.getText());
         // Get the amount spent on parking.
         parkingActual = Double.parseDouble(parkingFees.getText());
         // Get the amount spent on taxis.
         taxiActual = Double.parseDouble(taxiCharges.getText());
         // Get the conference registration.
         reg = Double.parseDouble(confReg.getText());
         // Get the amount spent per night on lodging.
         lodgingPerNightActual = Double.parseDouble(lodging.getText());
      }

      /**
         The determineActualExpenses method calculates the
         expenses.
         @return The amount of expenses.
      */

      private double determineActualExpenses()
      {
         double actual = (days * DAILY_MEAL_RATE) + parkingActual + taxiActual +
                         (miles * MILEAGE_RATE) + (lodgingPerNightActual * days) +
                         carRentalActual + air + reg;
         return actual;
      }

      /**
         The determineReimbursement method determines
         the allowable reimbursement.
         @return The amount of reimbursement.
      */

      private double determineReimbursement()
      {
         double reimbursable;

         // Calculate meal reimbursement.
         mealsRe = days * DAILY_MEAL_RATE;

         // Determine parking fee reimbursement.
         if (parkingActual > (MAX_PARKING_FEES * days))
            parkingRe = MAX_PARKING_FEES * days;
         else
            parkingRe = parkingActual;

         // Determine taxi charge reimbursement.
         if (taxiActual > (MAX_TAXI * days))
            taxiRe = MAX_TAXI * days;
         else
            taxiRe = taxiActual;

         // Determine lodging reimbursement.
         if (lodgingPerNightActual > MAX_LODGING_PER_NIGHT)
            lodgingRe= MAX_LODGING_PER_NIGHT * days;
         else
            lodgingRe = lodgingPerNightActual * days;

         reimbursable = (days * DAILY_MEAL_RATE) + parkingRe + taxiRe +
                        (miles * MILEAGE_RATE) + lodgingRe +
                        carRentalActual + air + reg;

         return reimbursable;

      }
   }  // end of inner class

   /**
      ResetButtonListener is an action listener class for the
      resetButton component.
   */

   private class ResetButtonListener implements ActionListener
   {
      /**
         actionPerformed method
         @param e An ActionEvent object.
      */

      public void actionPerformed(ActionEvent e)
      {
         // Reset all the text fields to 0.
         numDays.setText("0");
         airfare.setText("0");
         carRental.setText("0");
         milesDriven.setText("0");
         parkingFees.setText("0");
         taxiCharges.setText("0");
         confReg.setText("0");
         lodging.setText("0");
      }
   } // End of inner class
}
