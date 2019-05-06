package services;

import dao.Expenses;
import logic.Expense;

import java.util.List;

public class ExpenseService {
    private static Expenses expenses;

    public ExpenseService() throws Exception{
        this.expenses = new Expenses();
    }

    public void addExpense (Expense expense) throws Exception {
        expenses.addObject(expense);
    }

    public List<Expense> getAllExpenses() throws Exception{
        return expenses.getAllObjects();
    }

}
