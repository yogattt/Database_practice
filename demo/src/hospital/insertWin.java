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
import javax.swing.table.DefaultTableModel;

public class insertWin extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel ;
	private JButton ok;
	insertWin(int selectnum,info in,mainWindow mw,Connection con)
	{
		this.setTitle("添加记录");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		String[][] values= {};
		tableModel = new DefaultTableModel(values,in.workertable[selectnum]);
		table = new JTable(tableModel);
		table.setRowHeight(30);
		JScrollPane scrollPane = new JScrollPane(table);   //支持滚动
		this.add(scrollPane); //,BorderLayout.CENTER
       // table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
        scrollPane.setViewportView(table);
		String []value= {};
		tableModel.addRow(value);
		ok=new JButton("确认");
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					String columname=in.tablename[selectnum]+"(",valuename=" ";
					for(int i=0;i<in.columnName[selectnum].length;i++)
						if(table.getValueAt(0,i)!=null)
						{
							columname+=in.columnName[selectnum][i]+",";
							valuename+="\'"+table.getValueAt(0,i)+"\',";
						}
					if(valuename.length()>" ".length())
					{
						CallableStatement Call;
						try {
							String cur1=columname.substring(0, columname.length()-1),
									cur2=valuename.substring(0, valuename.length()-1);
							cur1+=")";
							Call = con.prepareCall("{call INSERTTABLE(?,?,?)}");
							Call.setString(1, cur1);
				            Call.setString(2, cur2);
				            Call.registerOutParameter(3, oracle.jdbc.OracleTypes.INTEGER);
				            // 执行
				            Call.execute();
				            if(Call.getInt(3)==1)
				            	System.out.print("插入成功\n");
				            else
				            	System.out.print("插入失败\n");
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
