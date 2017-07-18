package com.mjsearch.emma.resumeeditor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mjsearch.emma.resumeeditor.R;
import com.mjsearch.emma.resumeeditor.model.Experience;
import com.mjsearch.emma.resumeeditor.util.DateUtils;

import java.util.List;

/**
 * Created by emmazhuang on 5/17/17.
 */

public class ExpAdapter extends BaseAdapter implements OnClickListener {
    private Context context;
    private List<Experience> data;
    private MyClickListener listener;

    public interface MyClickListener{
        public void ClickListener(View v);
    }

    public void onClick(View v){
        listener.ClickListener(v);
    }


    public ExpAdapter(@NonNull Context context, List<Experience> data, MyClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int index) {
        return data.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    public View getView(int index, View contentView, ViewGroup parent) {
        ViewHolder vh;
        if(contentView==null) {
            vh = onCreateViewHolder(parent, index);
            contentView = vh.view;
            vh.view.setTag(vh);
        } else {
            vh = (ViewHolder) contentView.getTag();
        }
        onBindViewHolder(vh, index);
        return contentView;
    }

    protected void onBindViewHolder(ViewHolder vh, int index) {
        final View expView = vh.view;
        final Experience exp = (Experience) getItem(index);
        String dateString = DateUtils.dateToString(exp.startDate) + "~" + DateUtils.dateToString(exp.endDate);
        ((ViewHolder) vh).company.setText(exp.company+" "+"("+dateString+")");
        ((ViewHolder) vh).content.setText(exp.content);
        ((ViewHolder) vh).btn.setOnClickListener(this);
        ((ViewHolder) vh).btn.setTag(index);
      /*  ((ViewHolder) vh).edit_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(expView, exp);
            }
        });*/
    }

    protected ViewHolder onCreateViewHolder(ViewGroup parent, int index) {
        View view = LayoutInflater.from(context).inflate(R.layout.experience_item, parent, false);
        return new ViewHolder(view);
    }

    private class ViewHolder {
        //hold the view and TextView withing the element layout
        protected View view;
        TextView company;
        TextView content;
        ImageButton btn;

        public ViewHolder(@NonNull View view) {
            this.view = view;
            company = (TextView) view.findViewById(R.id.experience_company);
            content = (TextView) view.findViewById(R.id.experience_content);
            btn = (ImageButton) view.findViewById(R.id.edit_experience_btn);
        }
    }
}
