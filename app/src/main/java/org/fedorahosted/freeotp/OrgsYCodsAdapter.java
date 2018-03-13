package org.fedorahosted.freeotp;

/**
 * Created by marian on 13/03/18.
 */

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class OrgsYCodsAdapter extends BaseAdapter {

        Context context;
        String orgsList[];
        String codsList[];
        LayoutInflater inflter;

        public OrgsYCodsAdapter(Context applicationContext, String[] cods, String[] orgs) {
            this.context = applicationContext;
            this.orgsList = orgs;
            this.codsList = cods;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return orgsList.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.token, null);
            TextView code = (TextView)  view.findViewById(R.id.code);
            TextView organismo = (TextView)  view.findViewById(R.id.organismo);
            code.setText(codsList[i]);
            organismo.setText(orgsList[i]);

            return view;
        }
}
