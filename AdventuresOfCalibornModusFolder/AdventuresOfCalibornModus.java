import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import sylladex.*;

public class AdventuresOfCalibornModus extends FetchModus {

	private FetchModusSettings s;
	private ArrayList<SylladexCard> displarray;
	
	public AdventuresOfCalibornModus(Main m) {
		this.m = m;
		setModusSettings();
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if(e.getActionCommand().equals("eject"))
		{
			for(SylladexCard card : m.getCards())
			{
				if(!card.isEmpty())
					open(card);
			}
		}
	}

	private void setModusSettings(){
		s = new FetchModusSettings();
		s.set_author("Modus by InvertedVoid, \"Story\" by Chimera17");
		s.set_bottom_dock_image("modi/AdventuresOfCaliborn/dock.png");
		s.set_top_dock_image("modi/AdventuresOfCaliborn/dock_top.png");
		s.set_dock_text_image("modi/AdventuresOfCaliborn/text.png");
		s.set_card_image("modi/AdventuresOfCaliborn/cardiborn.gif");
		s.set_modus_image("modi/AdventuresOfCaliborn/modus.gif");
		s.set_card_back_image("modi/AdventuresOfCaliborn/back.gif");
		s.set_item_file("modi/items/adventuresofcaliborn.txt");
		s.set_card_size(94, 118);
		s.set_cards_draggable(true);
		s.set_draw_empty_cards(true);
		s.set_initial_card_number(10);
		s.set_name("The Adventures of Caliborn Modus");
		s.set_origin(0, 0);
	}
	
	@Override
	public FetchModusSettings getModusSettings() {
		return s;
	}

	@Override
	public void prepare() {
		populatePreferencesPanel();	
		
		icons = new ArrayList<JLabel>();
		displarray = new ArrayList<SylladexCard>();
		loadItems();
	}
	
	private void populatePreferencesPanel()
	{
		// We need absolute positioning.
		preferences_panel.setLayout(null);
		preferences_panel.setPreferredSize(new Dimension(270,300));
		
		JButton ejectbutton = new JButton("EJECT");
			ejectbutton.setActionCommand("eject");
			ejectbutton.addActionListener(this);
			ejectbutton.setBounds(66,7,162,68);
			preferences_panel.add(ejectbutton);
	}
	
	private void loadItems(){
			for(String string : items)
			{
				if(m.getNextEmptyCard()==null){ m.addCard(); }
				SylladexCard card = m.getNextEmptyCard();
				
				SylladexItem item = new SylladexItem(string, m);
				card.setItem(item);	
				JLabel icon = m.getIconLabelFromItem(item);
				card.setIcon(icon);
				icons.add(icon);
				displarray.add(card);
			}
			m.setIcons(icons);
			arrangeCards();
		}
	
	public void arrangeCards(){
		m.getCards().get(0).setPosition(new Point(0, 120));
		m.getCards().get(1).setPosition(new Point(100, 120));
		m.getCards().get(2).setPosition(new Point(200, 120));
		m.getCards().get(3).setPosition(new Point(150, 240));
		m.getCards().get(4).setPosition(new Point(100, 360));
		m.getCards().get(5).setPosition(new Point(50, 480));
		int i;
		for(i=6; i<m.getCards().size();i++)
		{
			m.getCards().get(i).setPosition(new Point(100*i-300, 120));
		}
		i=0;
		for(SylladexCard card : m.getCards())
		{
			card.setLayer(i++);
			card.getForeground().add(getPositionedJLabel("modi/AdventuresOfCaliborn/Calstripe.gif"));
			card.getForeground().setVisible(true);
		}
		m.setCardHolderSize(100*m.getCards().size(), 600);
	}
	
	private JLabel getPositionedJLabel(String path){
		JLabel label = new JLabel(Main.createImageIcon(path));
		label.setBounds(76, 39, 15, 68);
		label.setOpaque(true);
		label.setBackground(Color.BLACK);
		return label;
	}

	@Override
	public void addGenericItem(Object o) {
		if(m.getNextEmptyCard()==null){ return; }
		
		SylladexCard card = m.getNextEmptyCard();
		SylladexItem item = new SylladexItem("ITEM413", o, m);

		String name = item.getName();
		name = name.trim();
		name = name.toUpperCase();
		name = name.replace(" ", "");
		
		ArrayList<String> names = new ArrayList<String>(); //Really, this should be outside of this method, but do you really think I care about code elegancy?
		names.add("CALIBORN");
		names.add("CLAMIBORN");
		names.add("CALICORN");
		names.add("LORDENGLISH");
		names.add("LORDOFTIME");
		names.add("CALRAPBORN");
		names.add("LORDLIMEY");
		names.add("LORDSPANISH");
		names.add("LORDDEUTSCHE");
		names.add("LORDBRITISH");
		names.add("LORDFRENCH");
		names.add("DERIVATIVES");
		names.add("COOLIBORN");
		
		for(String string : names)
			if(name.contains(string)) return;
		
		card.setItem(item);
		JLabel icon = m.getIconLabelFromItem(item);
		card.setIcon(icon);
		displarray.add(card);
		icons.add(icon);
		m.setIcons(icons);
		arrangeCards();
	}

	@Override
	public void open(SylladexCard card) {
		if(!card.isEmpty())
		{
			ArrayList<JLabel> notIcons = new ArrayList<JLabel>(icons);
			icons.remove(card.getIcon());
			m.setIcons(icons);
			m.open(card);
			int ii=-1;
			do{ii++;}while(icons.get(ii)==notIcons.get(ii));
			for(int i = ii; i<displarray.size()-1; i++)
			{
				displarray.get(i).setItem(displarray.get(i+1).getItem());
				displarray.get(i).setIcon(displarray.get(i+1).getIcon());
				displarray.get(i+1).setIcon(null);
				displarray.get(i+1).setItem(null);
			}
			displarray.remove(displarray.size()-1);
			m.setIcons(icons);
			
			arrangeCards();
		}
		
		
		/*if(!card.isEmpty())
		{
			icons.remove(card.getIcon());
			m.setIcons(icons);
			m.open(card);			
			
			for(int i = 0; i<array.size()-1; i++)
			{
				if(array.get(i).isEmpty())
				{
					SylladexItem item = array.get(i+1).getItem();
					
					array.get(i).setItem(item);
					JLabel icon = m.getIconLabelFromItem(item);
					array.get(i).setIcon(icon);
					icons.set(i, icon);
					array.get(i).setAccessible(true);
					
					array.get(i+1).setIcon(null);
					array.get(i+1).setItem(null);
					icons.set(i+1, new JLabel(""));
				}
			}
			
			boolean anythingPresent = false;
			for(SylladexCard Card : array) //note the caps
			{
				if(Card.isEmpty())
					array.remove(Card);
				else
					anythingPresent = true;
			}			
			
			if(!anythingPresent)
			{
				icons.clear();
			}
			
			do;while(icons.remove(new JLabel("")));
			
			m.setIcons(icons);
			arrangeCards();
		}*/
		
	}

	@Override
	public void addCard() {
		m.addCard();
		arrangeCards();
	}

	@Override
	public void showSelectionWindow() {
		// TODO Auto-generated method stub
		//HAVE TO PUT A REFERENCE HERE SOMEWHERE
		//BUT HOW???
		//If I can't think of a really easy good one then no.
	}

	@Override
	public ArrayList<String> getItems() {
		
		ArrayList<String> i = new ArrayList<String>();
		
		for(SylladexCard card : displarray)
		{
			if(card.getItem() != null)
				i.add(card.getItem().getSaveString());
		}
		
		return i;
	}
	
	//[WIDGET]widgets/RPObject.class[/WIDGET]This here is the name of the item;C:\Users\matt\Documents\(Insert name here)\T_SchmidtMechaToken.png[NAME]This here is the name of the item
	
//	public void load(String savestring)
//	{
//		string = savestring.substring(0, savestring.indexOf(";"));
//		img = new File(savestring.substring(savestring.indexOf(";")+1));
//		if(img.exists())
//			setImages();
//	}
	
}
