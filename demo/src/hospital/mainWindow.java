package hospital;
import java.awt.Choice;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
public class mainWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Choice chc;
	private int selectnum;
	private DefaultTableModel tableModel;
    private JTable table;
    private JTextField pagenum;
    private int curpage=1,pagecount;
    private Label sumpage;
    private JButton pre,next,select,addrow,delrow,editB;
    CallableStatement cs = null;
    private info information;
    private Boolean edit;
	public mainWindow(Connection con)
	{
		this.connect=con;
		this.setTitle("病房管理系统");
		this.setSize(650, 360); 
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		information=new info();
		selectnum=0;
		edit=false;
		JPanel jPanel0 = new JPanel();
		sumpage=new Label();
		pagecount=1;
		chc=new Choice();chc.setSize(20, 10);
		chc.add("医护人员");chc.add("病人");chc.add("医疗记录");chc.add("科室信息");
		chc.add("医生患者");chc.add("住院信息");chc.add("住退记录");chc.add("病房信息");
		jPanel0.add(new Label("查询内容:"));
		jPanel0.add(chc);
		this.add(jPanel0);
		chc.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				information.where=" ";
				selectnum=chc.getSelectedIndex();
				tableModel.setColumnIdentifiers(information.workertable[selectnum]);
				curpage=1;
				update(false);
			}
        });
		String [][]tableVales= {};
		tableModel = new DefaultTableModel(tableVales,information.workertable[0]) {
			private static final long serialVersionUID = 1L;

			@Override  
            public boolean isCellEditable(int row,int column){
				if(column==0)
					return false;
				else
					return edit;
            }
		};
		tableModel.addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == TableModelEvent.UPDATE){
					try {
						int r=e.getFirstRow(),c=e.getColumn();
						if(c==-1)
							return;
						String str1=information.tablename[selectnum];
						String str2=information.columnName[selectnum][c]+"=\'"+table.getValueAt(r, c)+"\'";
					    String str3=information.columnName[selectnum][0]+"=\'"+table.getValueAt(r,0)+"\'";
						cs=connect.prepareCall("{call alterRow(?,?,?,?)}");
						cs.setString(1, str1);
						cs.setString(2, str2);
						cs.setString(3, str3);
						cs.registerOutParameter(4, oracle.jdbc.OracleTypes.INTEGER);
						cs.execute();
						if(cs.getInt(4)==1)
							System.out.print("修改成功");
						else
							System.out.print("修改失败");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
		      public void mouseClicked(MouseEvent e){
		    	  if(e.getClickCount()>=2)
		    	  {
		    		  int x=table.rowAtPoint(e.getPoint()),y=table.columnAtPoint(e.getPoint());
		    		  String v=(String) table.getValueAt(x, y);
		    		  new detialWin(selectnum,information,connect,x,y,v);
		    	  }
		      }
		    });
		table.setRowHeight(30);
		JScrollPane scrollPane = new JScrollPane(table);   //支持滚动
        this.add(scrollPane); //
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
        scrollPane.setViewportView(table);
        JPanel jp1=new JPanel();
        jp1.add(new Label("当前页数：第"));
        pagenum=new JTextField(3);
        pagenum.setText("1");
        jp1.add(pagenum);
        jp1.add(new Label("页"));
        pre=new JButton("<");next=new JButton(">");
        pre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(curpage<=1)
					return;
				curpage--;
				update(false);
			}
        });
        next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(curpage==pagecount)
					return;
				curpage++;
				update(false);
			}
        });
        		
        jp1.add(pre);jp1.add(next);
        jp1.add(sumpage);
        sumpage.setEnabled(false);
        select=new JButton("筛选");
        jp1.add(select);
        select.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					show(1);
				}
        	});
        addrow=new JButton("添加数据");
        addrow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				show(2);
			}
		});
        jp1.add(addrow);
        delrow=new JButton("删除数据");
        delrow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				show(3);
			}
		});
        editB=new JButton("编辑");
        editB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(edit)
				{
					edit=false;
					editB.setText("编辑");
				}
				else
				{
					edit=true;
					editB.setText("完成");
				}
			}
		});
        jp1.add(editB);
        jp1.add(delrow);
        this.add(jp1);
        update(false);
        }
	void show(int i)
	{
		if(i==1)
			new selectWin(selectnum,information,this);
		if(i==2)
			new insertWin(selectnum,information,this,connect);
		if(i==3)
			new deleteWin(selectnum,information,this,connect);
	}
	public void update(Boolean flag)
	{
		if(flag)
			curpage=1;
		try {
			while(tableModel.getRowCount()>0)
				tableModel.removeRow(tableModel.getRowCount()-1);
			cs = connect.prepareCall("{call paging_cursor(?,?,?,?,?,?,?)}");
			// 给in?赋值
            cs.setString(1, information.tablename[selectnum]);// 传表名
            cs.setInt(2, 7);// 传入pagesize，每页显示多少条记录
            cs.setInt(3, curpage);// 传入pagenow，显示第几页。
            cs.setString(4,information.where);
            // 给out?注册
            cs.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR);
            cs.registerOutParameter(6, oracle.jdbc.OracleTypes.INTEGER);
            cs.registerOutParameter(7, oracle.jdbc.OracleTypes.INTEGER);
            // 执行
            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(5);
            while (rs.next()) {
            	List<String> list=new ArrayList<String>();
            	for(String i : information.columnName[selectnum])
            		list.add(rs.getString(i));
            	tableModel.addRow(list.toArray());
            }
            // 取出总页数
            int pageCount = cs.getInt(7);
            sumpage.setText("总页数"+String.valueOf(pageCount));
            pagenum.setText(String.valueOf(curpage));
            pagecount=pageCount;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
