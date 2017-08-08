package contactui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zws.ble.contacthuawei.R;

import util.StringUtils;


public class SortAdapter extends BaseAdapter implements SectionIndexer {

	public List<SortModel> getList() {
		return list;
	}

	private List<SortModel> list = null;
	private List<SortModel> list_array = new ArrayList<>();
	private List<SortModel> selectList = new ArrayList<>();

	private Context mContext;
	public CheckboxClickListen checkboxClickListen;

    public List<SortModel> getSelectList() {
        return selectList;
    }

	public void setSelectList(List<SortModel> selectList, int type) {
		if (type == 1){
			this.selectList.addAll(selectList);
		}else if (type == 2){
			this.selectList.removeAll(selectList);
		}else if (type == 3){
            this.selectList = selectList;
        }else if (type == 4){
			this.selectList.clear();
		}
	}

	public SortAdapter(Context mContext,List<SortModel> list){
		this.mContext = mContext;
		this.list = list;
	}
	public void updateListView(List<SortModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		if (convertView== null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
			viewHolder.check_box = (CheckBox) convertView.findViewById(R.id.checkbox_item);
			viewHolder.tv_copy = (TextView) convertView.findViewById(R.id.copy_item);
			viewHolder.tv_phome = (TextView) convertView.findViewById(R.id.mobile_item);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.name_item);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		int section = getSectionForPosition(position);

		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		viewHolder.tvTitle.setText(mContent.getName());
		if (mContent.getMobilePhone().size()!=0)
		viewHolder.tv_phome.setText(mContent.getMobilePhone().get(0));
		viewHolder.tv_name.setText(mContent.getName().substring(0,1));
        int colorRan = position%3;
		if (colorRan == 0){
			viewHolder.tv_name.setBackgroundResource(R.drawable.circle_pink);
		}else if (colorRan == 1){
			viewHolder.tv_name.setBackgroundResource(R.drawable.circle_red);
		}else if (colorRan == 2){
			viewHolder.tv_name.setBackgroundResource(R.drawable.circle);
		}
		//checkbox重复问题
		if(mContent.getChecked()){
			viewHolder.check_box.setChecked(true);
		}else {
			viewHolder.check_box.setChecked(false);
		}
		viewHolder.check_box.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                checkboxClickListen.onItemCheck(mContent.getChecked());
				// TODO Auto-generated method stub
				if(mContent.getChecked()){
					mContent.setChecked(false);
					selectList.remove(mContent);
				}else {
					mContent.setChecked(true);
					selectList.add(mContent);
				}
			}
		});
		return convertView;
	}

	@Override
	public Object[] getSections() {
		return null;
	}
	/**
	 * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
	 */
	@Override
	public int getPositionForSection(int sectionIndex) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == sectionIndex) {
				return i;
			}
		}
		
		return -1;
	}

	/**
	 * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��Char asciiֵ
	 */
	@Override
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}


	final static class ViewHolder{
		TextView tvLetter;
		TextView tvTitle;
		TextView tv_phome;
		TextView tv_name;
		TextView tv_copy;
		CheckBox check_box;
	}
	/**
	 * ��ȡӢ�ĵ�����ĸ����Ӣ����ĸ��#���档
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 排序
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	interface CheckboxClickListen{
		void onItemCheck(boolean checkBox);
	}

	public void setCheckboxClick(CheckboxClickListen checkboxClickListen){
		this.checkboxClickListen = checkboxClickListen;
	}
}
