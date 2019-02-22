package com.gino.citireapometre;

import java.util.List;

import com.gino.citireapometre.DB.ApometreDataSource;
import com.gino.citireapometre.DB.Camera;
import com.gino.citireapometre.DB.CamereListAdapter;
import com.gino.citireapometre.DB.Persoana;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SetariFragmentActivity extends Fragment implements FragmentLifecycle
{		
	private ApometreDataSource datasource;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
        View view = inflater.inflate(R.layout.activity_setari_fragment, container, false);   
        datasource = new ApometreDataSource(this.getActivity());
  		datasource.open();
        //////////
        ///////// Regiunea legata de Camere
      //salveaza si la enter
      		TextView txtCamera=(TextView)view.findViewById(R.id.txtNumeCamera);
      		txtCamera.setOnKeyListener(new OnKeyListener()
      		{
      			@Override
      			public boolean onKey(View v, int keyCode, KeyEvent event)
      			{
      				 if (event.getAction()==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) 
      				 {
      					 AddCamera();
      	                 return true;
      				 }
      	             return false;
      			}
      		});
      		final Button btnCamere=(Button)view.findViewById(R.id.btnCamere);
      		final Button btnPersoana=(Button)view.findViewById(R.id.btnPersoana);
      		btnCamere.setSelected(true);
      		btnCamere.setOnClickListener(new OnClickListener()
			{				
				@Override
				public void onClick(View v)
				{
					btnPersoana.setSelected(false);
					btnCamere.setSelected(true);					
					SetUpTabs(1);					 
				}
			});
      		btnPersoana.setOnClickListener(new OnClickListener()
			{				
				@Override
				public void onClick(View v)
				{
					btnCamere.setSelected(false);
					btnPersoana.setSelected(true);
					SetUpTabs(2);					 
				}
			});
      		
      		List<Camera> values = datasource.getAllCamere();      		
      		CamereListAdapter adapter=new CamereListAdapter(this.getActivity(), R.layout.camera_list_item, values);
      		final ListView listSetari=(ListView)view.findViewById(R.id.listSetari);
      		listSetari.setAdapter(adapter);
      		
      		final ImageButton btnAdd=(ImageButton)view.findViewById(R.id.add);
      		btnAdd.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					AddCamera();					
				}
			});     		
      		   				
      		////////////
      		/////////
      		////sfarsit regiune camere
      		
      		
      		
      		////////
      		//////
      		////regiune Persoana
      		
      		Button btnSave = (Button)view.findViewById(R.id.btnSave);
      		final EditText txtName = (EditText)view.findViewById(R.id.txtNume);		
			final EditText txtBloc = (EditText)view.findViewById(R.id.txtBloc);	
			final EditText txtScara = (EditText)view.findViewById(R.id.txtScara);	
			final EditText txtEtaj = (EditText)view.findViewById(R.id.txtEtaj);	
			final EditText txtApartament = (EditText)view.findViewById(R.id.txtApartament);
			final EditText txtNrPersoane = (EditText)view.findViewById(R.id.txtNrPersoane);
			final EditText txtEmail = (EditText)view.findViewById(R.id.txtEmail);
      		btnSave.setOnClickListener(new  OnClickListener()
			{				
				@Override
				public void onClick(View v)
				{					
					Persoana persoana = datasource.GetPersoana();
					if(persoana!=null)
					{			
						datasource.updatePersoana(txtName.getText().toString(), txtBloc.getText().toString(),
								txtScara.getText().toString(),txtEtaj.getText().toString(),
								txtApartament.getText().toString(),txtNrPersoane.getText().toString(),
								txtEmail.getText().toString());
					}
					else 
					{
						datasource.createPersoana(txtName.getText().toString(), txtBloc.getText().toString(),
								txtScara.getText().toString(),txtEtaj.getText().toString(),
								txtApartament.getText().toString(),txtNrPersoane.getText().toString(),
								txtEmail.getText().toString());
					}
					Toast toast = Toast.makeText(v.getContext(), getActivity().getResources().getString(R.string.Salvare_efectuata), Toast.LENGTH_SHORT);
					toast.show();					
				}
			});
      		
      		Persoana persoana = datasource.GetPersoana();
    		
    		if(persoana!=null)
    		{       			
    			txtName.setText(persoana.getName()); 			
    			txtBloc.setText(persoana.getBloc());    
    			txtScara.setText(persoana.getScara());    
    			txtEtaj.setText(persoana.getEtaj());    
    			txtApartament.setText(persoana.getApartament());  	
    			txtNrPersoane.setText(persoana.getNrPersoane());  	
    			txtEmail.setText(persoana.getEmail());
    		}
      		
      		///////
      		/////
      		///sfarsit regiune persoana
        
        return view;
    }
	public void SetUpTabs(int tab)
	{ 
		LinearLayout layoutCamere = (LinearLayout)this.getActivity().findViewById(R.id.layoutCamere);
		ScrollView layoutPersoana = (ScrollView)this.getActivity().findViewById(R.id.layoutPersoana);
		switch (tab)
        {
			case 1:
			{				
				layoutPersoana.setVisibility(View.GONE);
				layoutCamere.setVisibility(View.VISIBLE);	
				break;
			}
			case 2:
			{
				layoutCamere.setVisibility(View.GONE);
				layoutPersoana.setVisibility(View.VISIBLE);
				break;
			}			
		}
	}
	public void AddCamera()
	{
		TextView txtCamera=(TextView)this.getActivity().findViewById(R.id.txtNumeCamera);	
		if(!txtCamera.getText().toString().equals(""))
		{
			datasource.open();
			ListView listSetari=(ListView)this.getActivity().findViewById(R.id.listSetari);		
		    CamereListAdapter adapter = (CamereListAdapter) listSetari.getAdapter();
		    Camera Camera = null;
	
		            
		     
		      // save the new Camera to the database
		      Camera = datasource.createCamera(txtCamera.getText().toString());
		      adapter.add(Camera);
		      txtCamera.setText("");
	
		    //adapter.notifyDataSetChanged();//cica se refresuiesc toate view-urile care reflecta datasource-u (adica listview-ul din pagina asta)
		}
	}	
	@Override
    public void onResumeFragment()
    {
			    
    }
	
	
	    
}
