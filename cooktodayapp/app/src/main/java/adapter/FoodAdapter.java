package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.animee.cooktodayapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import activity.FoodDetailActivity;
import activity.FoodsActivity;
import entity.FoodEntity;

public class FoodAdapter extends BaseAdapter {
    private Context mContext;
    private List<FoodEntity> mFoodEntityList = new ArrayList<>();

    public FoodAdapter(Context context) {this.mContext = context ;}

    // set data updated screen
    public void setData(List<FoodEntity> mFoodEntityList){
        this.mFoodEntityList = mFoodEntityList;
        notifyDataSetChanged();
    }
    // get item total number
    @Override
    public int getCount() { return mFoodEntityList == null ? 0: mFoodEntityList.size(); }

    // get item object according to position
    @Override
    public FoodEntity getItem(int position){
        return mFoodEntityList == null ? null : mFoodEntityList.get(position); }

        // get item id acorrding to position
        @Override
    public long getItemId(int position) { return position; }

    // get item view accordint to posiiton. convertView is view for screen out item
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        // use convertView
        if (convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_item,
                    null);
            vh.foodNameTv = (TextView) convertView.findViewById(R.id.foodNameTv);
            vh.tasteTv = (TextView) convertView.findViewById(R.id.tasteTv);
            vh.yclTv = (TextView) convertView.findViewById(R.id.yclTv);
            vh.foodTypeTv = (TextView) convertView.findViewById(R.id.goodTypeTv);
            vh.foodPicIv = (ImageView) convertView.findViewById(R.id.foodPicIv);
            convertView.setTag(vh);
        }
        else{
            vh = (ViewHolder) convertView.getTag();
        }
        final  FoodEntity bean = getItem(position);
        if (bean != null) {
            vh.foodNameTv.setText(bean.getFoodName());
            vh.tasteTv.setText("Taste" + bean.getTaste());
            vh.yclTv.setText("Meterial" + bean.getYcl());
            vh.foodTypeTv.setText("type" + commmonUtils.getFoodTypeStr(bean.getFoodType()));

            Glide.with(mContext).load(bean.getFoodPic()).into(vh.foodPicIv);
            vh.foodPicIv.setOnClickListerner(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, FoodDetailActivity.class);
                    intent.putExtra("foodEntity", bean);
                    mContext.startActivity(intent);
                }
            });
            vh.foodNameIv.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(mContext,
                            FoodDetailActivity.class);
                    intent.putExtra("foodEntity", bean);
                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
    }
    class ViewHolder{
        public TextView foodNameTv, tasteTv, yclTv,foodTypeTv;
        public ImageView foodPicIv;

    }
}


























