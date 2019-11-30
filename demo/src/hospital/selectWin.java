package hospital;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class selectWin extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel ;
	private JButton ok;
	selectWin(int selectnum,info in,mainWindow mw)
	{
		this.setTitle("查询");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		String[][] values= {};
		tableModel = new DefaultTableModel(values,in.workertable[selectnum]);
		table = new JTable(tableModel);
		table.setRowHeight(30);
		JScrollPane scrollPane = new JScrollPane(table);   //支持滚动
		this.add(scrollPane); 
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
        scrollPane.setViewportView(table);
		String []value= {};
		tableModel.addRow(value);
		ok=new JButton("确认");
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					String cur=" ";
					for(int i=0;i<in.columnName[selectnum].length;i++)
						if(table.getValueAt(0,i)!=null)
							cur+=in.columnName[selectnum][i]+"=\'"+table.getValueAt(0,i)+"\' and ";
					if(cur.length()>" ".length())
					{
						cur=cur.substring(0, cur.length()-4);
						in.where="  where "+cur;
					}
					else
						in.where=" ";
					mw.update(true);
					dispose();//本窗口销毁,释放内存资源
				}
		});
		this.add(ok);
		this.setSize(600,122);
		this.setVisible(true);
	}
}
