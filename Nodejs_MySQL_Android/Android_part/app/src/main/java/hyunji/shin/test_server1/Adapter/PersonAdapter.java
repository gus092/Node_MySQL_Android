package hyunji.shin.test_server1.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hyunji.shin.test_server1.Interface.IPersonClickListner;
import hyunji.shin.test_server1.Model.Person;
import hyunji.shin.test_server1.R;

public class PersonAdapter extends RecyclerView.Adapter <PersonAdapter.MyViewHolder>{

    Context context;
    List<Person> personList;

    public PersonAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }


    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        CardView root_view;
        TextView name,phone,email;

        IPersonClickListner personClickListener;

        public void setPersonClickListener(IPersonClickListner personClickListener) {
            this.personClickListener = personClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            root_view = (CardView)itemView.findViewById(R.id.root_view);

            name = (TextView)itemView.findViewById(R.id.text_name);
            phone = (TextView)itemView.findViewById(R.id.text_phone);
            email = (TextView)itemView.findViewById(R.id.text_email);

            itemView.setOnClickListener(this);//다른부분

        }

        @Override
        public void onClick(View view) {
            personClickListener.onPersonClick(view,getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.person_layout,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.name.setText(personList.get(i).getName());
        myViewHolder.email.setText(personList.get(i).getEmail());
        myViewHolder.phone.setText(personList.get(i).getPhone());

        if(i % 2 ==0 )
            myViewHolder.root_view.setCardBackgroundColor(Color.parseColor("#E1E1E1"));

        myViewHolder.setPersonClickListener(new IPersonClickListner() {
            @Override
            public void onPersonClick(View view, int position) {
                Toast.makeText(context,""+personList.get(position).getName(),Toast.LENGTH_LONG).show();
            }
        });

    }

}
