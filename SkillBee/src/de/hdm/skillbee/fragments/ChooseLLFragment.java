package de.hdm.skillbee.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import de.hdm.skillbee.R;
import de.hdm.skillbee.bo.Knoten;
import de.hdm.skillbee.bo.Learningline;
import de.hdm.skillbee.controller.ControllerDBLokal;
import de.hdm.skillbee.controller.ControllerDBServer;
import de.hdm.skillbee.db.server.JSONParser;

 
public class ChooseLLFragment extends Fragment implements OnItemClickListener {

    Activity baseActivity;
    boolean state = false;
    Vector<Learningline> llVector = null;
    Learningline selectedLL = null;
    ControllerDBLokal cdbl = null;
	ControllerDBServer cdbs = null;
	
	JSONParser jsonParser = new JSONParser();
	String userMsg = null;
	
	Adapterwithouticons adapter;
    private List<RowItemLLOverview> rowItemslloverview;
	
	boolean success1 = false;
	
	ListView lv = null;
	
    
    public void setBaseActivity(Activity baseActivity) {
		this.baseActivity = baseActivity;
	}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
   	 View view = inflater.inflate(R.layout.fragment_store_load, container, false);
   	 lv = (ListView)view.findViewById(R.id.storeloadlist);
   	 doSomething();

       return view;
   }

    public void doSomething() {
        
    	cdbl = ControllerDBLokal.get();
    	cdbs = ControllerDBServer.get();
        
        if (state) {
        	buildUploadList();
        }
        else {
        	buildDownloadList();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    	
    	
		selectedLL = llVector.elementAt(position);
		if (state) {
			saveSelected2OnlineDB();
		} else {
			saveSelected2LocalDB();
		}
		

    }
    
    public void setDownload() {
    	state = false;

    }

    public void setUpload() {
    	state = true;

    }
    
    public void saveSelected2LocalDB() {
    	new DownloadSelectedLL().execute();
    }
    
    public void saveSelected2OnlineDB() {
    	new UploadLL().execute();
    }
    
    public void buildDownloadList() {
    	new  DownloadLLs().execute();
    }
    
    public void buildUploadList() {
    	llVector = cdbl.getLearninglineMapper().findAll();
    	setRows();
    }
    
    public void setRows() {
    	rowItemslloverview = new ArrayList<RowItemLLOverview>();
        
        if(llVector!=null && llVector.size() != 0) {
	        for (int i = 0; i < llVector.size(); i++) {
	            RowItemLLOverview items = new RowItemLLOverview(llVector.elementAt(i).getBezeichnung());
	
	            rowItemslloverview.add(items);
	        }
        }
        adapter = new Adapterwithouticons(getActivity(), rowItemslloverview);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }
    
    private class DownloadLLs extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			success1 = false;
			
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("query", ChooseLLFragment.this.cdbs.getLearninglineMapper().findAllQuery()));
			params1.add(new BasicNameValuePair("flag", "1"));
			JSONObject json = jsonParser.makeHttpRequest(cdbs.getUrl_learningline_queries(), "POST", params1);
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {
					// Success = 1 -> Means that no probs
				} else {
					userMsg = "Es konnte keine Online-Verbindung hergestellt werden";
					success1 = false;
					return null;
				}
				
				llVector = ChooseLLFragment.this.cdbs.getLearninglineMapper().json2Learningline(json);
				
			} catch (JSONException e) {
				userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				success1 = false;
				return null;
			}
			success1 = true;
			return null;
		}
		
		protected void onPostExecute(Void result) {
			
			if(success1) {
				setRows();
			}
			else {
				Toast einToast = Toast.makeText(ChooseLLFragment.this.baseActivity, userMsg, Toast.LENGTH_LONG);
				einToast.show();
			}
   
		}
	}
    
    private class UploadLL extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			
			//Falls es die LL bereits Online gibt
			if (selectedLL.getIdOn() > 0) {
				//Alte Knoten löschen
				params1.add(new BasicNameValuePair("query", ChooseLLFragment.this.cdbs.getKnotenMapper().deleteByLLQuery(selectedLL)));
				params1.add(new BasicNameValuePair("flag", "0"));
				JSONObject json = jsonParser.makeHttpRequest(cdbs.getUrl_knoten_queries(), "POST", params1);
				
				try {
					// Checking for SUCCESS TAG
					int success = json.getInt("success");

					if (success == 1) {
						// Success = 1 -> Means that no probs
					} else {
						userMsg = "Es konnte keine Online-Verbindung hergestellt werden";
						return null;
					}
					
				} catch (JSONException e) {
					userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
					return null;
				}
				
				//Alte LL updaten
				params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("query", ChooseLLFragment.this.cdbs.getLearninglineMapper().updateQuery(selectedLL)));
				params1.add(new BasicNameValuePair("flag", "0"));
				json = jsonParser.makeHttpRequest(cdbs.getUrl_learningline_queries(), "POST", params1);
				
				try {
					// Checking for SUCCESS TAG
					int success = json.getInt("success");

					if (success == 1) {
						// Success = 1 -> Means that no probs
					} else {
						userMsg = "Es konnte keine Online-Verbindung hergestellt werden";
						return null;
					}
					
				} catch (JSONException e) {
					userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
					success1 = false;
					return null;
				}
				
				//Neue Knoten uploaden
				Vector<Knoten> knotenVector = cdbl.getKnotenMapper().findByLL(selectedLL);
				if (knotenVector == null) {
					userMsg ="Die LearningLine wurde erfolgreich hochgeladen und überspeichert";
					return null;
				}
				for (Knoten k : knotenVector) {
					params1 = new ArrayList<NameValuePair>();
					params1.add(new BasicNameValuePair("query", ChooseLLFragment.this.cdbs.getKnotenMapper().insertQuery(k)));
					params1.add(new BasicNameValuePair("flag", "0"));
					json = jsonParser.makeHttpRequest(cdbs.getUrl_knoten_queries(), "POST", params1);
					
					try {
						// Checking for SUCCESS TAG
						int success = json.getInt("success");

						if (success == 1) {
							// Success = 1 -> Means that no probs
						} else {
							userMsg = "Es konnte keine Online-Verbindung hergestellt werden";
							return null;
						}
						
					} catch (JSONException e) {
						userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
						return null;
					}
				}
				
				userMsg ="Die LearningLine wurde erfolgreich hochgeladen und überspeichert";
			}
			else {
				//Neue LL hochladen
				params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("query", ChooseLLFragment.this.cdbs.getLearninglineMapper().insertQuery(selectedLL)));
				params1.add(new BasicNameValuePair("flag", "0"));
				JSONObject json = jsonParser.makeHttpRequest(cdbs.getUrl_learningline_queries(), "POST", params1);
				
				try {
					// Checking for SUCCESS TAG
					int success = json.getInt("success");

					if (success == 1) {
						// Success = 1 -> Means that no probs
					} else {
						userMsg = "Es konnte keine Online-Verbindung hergestellt werden";
						return null;
					}
					
				} catch (JSONException e) {
					userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
					success1 = false;
					return null;
				}
				
				//Neue LL-onID abfragen
				params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("query", ChooseLLFragment.this.cdbs.getLearninglineMapper().llIDQuery(selectedLL)));
				params1.add(new BasicNameValuePair("flag", "1"));
				json = jsonParser.makeHttpRequest(cdbs.getUrl_maxid_queries(), "POST", params1);
				
				try {
					// Checking for SUCCESS TAG
					int success = json.getInt("success");

					if (success == 1) {
						// Success = 1 -> Means that no probs
						selectedLL.setIdOn(cdbs.getLearninglineMapper().json2NewID(json));
						cdbl.getLearninglineMapper().insertFromOnlineDB(selectedLL);
						cdbl.getKnotenMapper().updateAfterRelease(selectedLL);
					} else {
						userMsg = "Es konnte keine Online-Verbindung hergestellt werden";
						return null;
					}
					
				} catch (JSONException e) {
					userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
					success1 = false;
					return null;
				}
				
				//Neue Knoten uploaden
				Vector<Knoten> knotenVector = cdbl.getKnotenMapper().findByLL(selectedLL);
				if (knotenVector == null) {
					userMsg ="Die LearningLine wurde erfolgreich hochgeladen";
					return null;
				}
				for (Knoten k : knotenVector) {
					params1 = new ArrayList<NameValuePair>();
					params1.add(new BasicNameValuePair("query", ChooseLLFragment.this.cdbs.getKnotenMapper().insertQuery(k)));
					params1.add(new BasicNameValuePair("flag", "0"));
					json = jsonParser.makeHttpRequest(cdbs.getUrl_knoten_queries(), "POST", params1);
					
					try {
						// Checking for SUCCESS TAG
						int success = json.getInt("success");

						if (success == 1) {
							// Success = 1 -> Means that no probs
						} else {
							userMsg = "Es konnte keine Online-Verbindung hergestellt werden";
							return null;
						}
						
					} catch (JSONException e) {
						userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
						return null;
					}
				}
				
				userMsg ="Die LearningLine wurde erfolgreich hochgeladen";
				
			}
			return null;
		}
		
		protected void onPostExecute(Void result) {
			
			Toast einToast = Toast.makeText(ChooseLLFragment.this.baseActivity, userMsg, Toast.LENGTH_LONG);
			einToast.show();
			
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			MainMenuFragment mf = new MainMenuFragment();
        	mf.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, mf);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
			
   
		}
	}
    
    private class DownloadSelectedLL extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			success1 = false;
			
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("query", ChooseLLFragment.this.cdbs.getKnotenMapper().findByLLOnIDQuery(selectedLL.getIdOn())));
			params1.add(new BasicNameValuePair("flag", "1"));
			JSONObject json = jsonParser.makeHttpRequest(cdbs.getUrl_knoten_queries(), "POST", params1);
			
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt("success");

				if (success == 1) {
					// Success = 0 -> Means that no probs
				} else {
					userMsg = "Es konnte keine Online-Verbindung hergestellt werden";
					return null;
				}
				
				Vector<Knoten> knVector = cdbs.getKnotenMapper().json2Knoten(json);
				
				//Falls es diese LL bereits Lokal gibt
				if (cdbl.getLearninglineMapper().findByCompositeKey(selectedLL.getId(), selectedLL.getIdOn()) != null) {
					cdbl.getLearninglineMapper().update(selectedLL);
					cdbl.getKnotenMapper().deleteByLL(selectedLL);					
				}
				else {
					cdbl.getLearninglineMapper().insertFromOnlineDB(selectedLL);
				}
				
				for(Knoten k : knVector) {
					cdbl.getKnotenMapper().insert(k);
				}
				
			} catch (JSONException e) {
				userMsg = "Es gab ein Problem bei der JSON-Typkonvertierung: " + e.getMessage();
				return null;
			}
			userMsg = "Download war erfolgreich!";
			return null;
		}
		
		protected void onPostExecute(Void result) {
			
			Toast einToast = Toast.makeText(ChooseLLFragment.this.baseActivity, userMsg, Toast.LENGTH_LONG);
			einToast.show();
			
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			MainMenuFragment mf = new MainMenuFragment();
        	mf.setBaseActivity(baseActivity);
        	fragmentTransaction.replace(R.id.fragmentcontainer, mf);
        	fragmentTransaction.addToBackStack(null);
         	fragmentTransaction.commit();
   
		}
	}

}
