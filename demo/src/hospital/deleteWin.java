package hospital;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class deleteWin extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel ;
	private JButton ok;
	deleteWin(int selectnum,info in,mainWindow mw,Connection con)
	{
		this.setTitle("删除数据");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		String[][] values= {};
		tableModel = new DefaultTableModel(values,in.workertable[selectnum]);
		table = new JTable(tableModel);
		table.setRowHeight(30);
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
        scrollPane.setViewportView(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		String []value= {};
		tableModel.addRow(value);
		ok=new JButton("确认");
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					String tablename=in.tablename[selectnum],where=" ";
					for(int i=0;i<in.columnName[selectnum].length;i++)
						if(table.getValueAt(0,i)!=null)
							where+=in.columnName[selectnum][i]+"=\'"+table.getValueAt(0,i)+"\' and";
					if(where.length()>" ".length())
					{
						CallableStatement Call;
						try {
							Call = con.prepareCall("{call delrow(?,?,?)}");
							Call.setString(1, tablename);
							where=where.substring(0, where.length()-4);
				            Call.setString(2, where);
				            Call.registerOutParameter(3, oracle.jdbc.OracleTypes.INTEGER);
				            Call.execute();
				            if(Call.getInt(3)==1)
				            	System.out.print("删除成功\n");
				            else
				            	System.out.print("删除失败\n");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					mw.update(false);
					dispose();
				}
		});
		this.add(ok);
		this.setSize(600,122);
		this.setVisible(true);
	}
}

