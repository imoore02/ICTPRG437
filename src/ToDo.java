// IMPORT STATEMENT:
/* 
 * to take a class or all classes visible for a program specified under a package - enabling the use of a class without writing the entire definition;
 * it is similar to a library in c;
 * the use of a '*' refers to all packages within the main package;
*/

import java.awt.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

// CLASS:
/*  
 * defines the type of an object - blueprint for objects
 * blank structure
*/

public class ToDo
{
	static JFrame 					frame = new JFrame("To-Do"); //generates a window;
	static DefaultListModel<String> tasks = new DefaultListModel<String>(); //generates a list;
	static JList<String> 			taskList = new JList<String>(tasks);
	static String 					taskTitle;

	public static void main(String[] args)
	{
		try {
			UIManager.setLookAndFeel(
			UIManager.getCrossPlatformLookAndFeelClassName());
		 } catch (Exception e) { 
		 }
		JPanel 		buttonPanel = new JPanel();
		JPanel		filePanel = new JPanel();
		JScrollPane	jpane = new JScrollPane();

		JButton		newTask = new JButton("Enter Task");
		JButton		deleteTask = new JButton("Delete Task");
		JButton		editTask = new JButton("Edit");
	
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setLocation(400, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		newTask.addActionListener(e -> ToDo.newTaskEvent());
		editTask.addActionListener(e -> ToDo.editTaskEvent());
		deleteTask.addActionListener(e -> ToDo.deleteTaskEvent());

		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(newTask);
		buttonPanel.add(editTask);
		buttonPanel.add(deleteTask);
		
		buttonPanel.setBorder(new EmptyBorder(0, 60, 10, 60));
		taskList.setBorder(new EmptyBorder(0, 0, 0, 0));
		jpane.setViewportView(taskList);

		frame.add(buttonPanel, BorderLayout.CENTER);
		frame.add(jpane, BorderLayout.SOUTH);
		frame.add(filePanel, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
	}

	private static void UpdateGUI()
	{
		ToDo.taskList.revalidate();
		ToDo.frame.pack();
	}

	private static void logger(String msg)
	{
		System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName() + "(): " + msg);
	}

	private static void newTaskEvent() 
	{
		ToDo.taskTitle = JOptionPane.showInputDialog(frame, "What do you have to do?");

		if (ToDo.taskTitle == null) 
		{
			ToDo.logger("CANCEL Button pressed");
		} 
		else if (ToDo.taskTitle.equals("")) 
		{
			ToDo.logger("OK Button pressed");
			JOptionPane.showMessageDialog(null, "Please assign the task a name.");
			newTaskEvent();
		}
		else 
		{
			ToDo.logger("Adding new task " + ToDo.taskTitle);
			tasks.addElement(ToDo.taskTitle);
			ToDo.UpdateGUI();
		}
	}
	
	private static void editTaskEvent()
	{
		int list_index = taskList.getSelectedIndex();
		
		if (list_index < 0)
		{
			JOptionPane.showMessageDialog(null, "Select a task to edit.");
		}
		else if (list_index >= 0)
		{
			ToDo.logger("Old Task Name - " + tasks.getElementAt(list_index));
			ToDo.taskTitle = JOptionPane.showInputDialog(frame, "Enter the new task title: ");
			
			if (ToDo.taskTitle == null) 
			{
				ToDo.logger("CANCEL Button pressed");
			}
			else if (ToDo.taskTitle.equals(""))
			{
				ToDo.logger("OK Button pressed");
			}
			else 
			{
				ToDo.logger("New Task Name - " + ToDo.taskTitle);
				tasks.setElementAt(ToDo.taskTitle, list_index);
				ToDo.UpdateGUI();
			}
		}
	}

	private static void deleteTaskEvent() 
	{
		int list_index = taskList.getSelectedIndex();
		
		if (list_index < 0)
		{
			ToDo.logger("Cannot find selected task or out of bounds");
			JOptionPane.showMessageDialog(null, "Select a task to delete.");
		}
		else if (list_index >= 0)
		{
			int response = JOptionPane.showConfirmDialog(null, "Delete task?", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.NO_OPTION);
			if (response == JOptionPane.NO_OPTION)
			{
				ToDo.logger("NO_OPTION Button clicked");
			}
			else if (response == JOptionPane.YES_OPTION)
			{
				ToDo.logger("Removing Task - " + tasks.getElementAt(list_index));
				tasks.remove(list_index);
				ToDo.UpdateGUI();
			}
			else if (response == JOptionPane.CLOSED_OPTION)
			{
				ToDo.logger("CLOSED_OPTION Button clicked");
			}
		}
	}
	
}
