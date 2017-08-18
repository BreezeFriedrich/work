package com.hysm.pojo;

public class Order {
	
	//订单表
	private int order_id,id,user_id,merchants_id,house_id,state,order_type,ammount_sum,payed_sum;
	private String openid,createtime,pay_time,cancel_time,check_in,check_out,idcard_id,tel;
	
	//房间表
	private int price_id,house_state,is_characteristic,is_choice;
	private String house_num,keyNum,house_name,introduce,geo_position,result,house_ctime,guarantee;
	
	//价格表
	private int pricing,single_price,week_price,month_price,three_price,five_price;
	
	private String nickname,m_name,check_in1,check_out1;
	
	private String safeguard,rule,name,ht_name,cardid,address,starttime,endtime,order_num;
	
	
	
	
	
	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	public String getSafeguard() {
		return safeguard;
	}

	public void setSafeguard(String safeguard) {
		this.safeguard = safeguard;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHt_name() {
		return ht_name;
	}

	public void setHt_name(String ht_name) {
		this.ht_name = ht_name;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	public String getCheck_in1() {
		return check_in1;
	}

	public void setCheck_in1(String check_in1) {
		this.check_in1 = check_in1;
	}

	public String getCheck_out1() {
		return check_out1;
	}

	public void setCheck_out1(String check_out1) {
		this.check_out1 = check_out1;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	

	
	
	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHouse_id() {
		return house_id;
	}

	public void setHouse_id(int house_id) {
		this.house_id = house_id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getOrder_type() {
		return order_type;
	}

	public void setOrder_type(int order_type) {
		this.order_type = order_type;
	}

	public int getAmmount_sum() {
		return ammount_sum;
	}

	public void setAmmount_sum(int ammount_sum) {
		this.ammount_sum = ammount_sum;
	}

	public int getPayed_sum() {
		return payed_sum;
	}

	public void setPayed_sum(int payed_sum) {
		this.payed_sum = payed_sum;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getCancel_time() {
		return cancel_time;
	}

	public void setCancel_time(String cancel_time) {
		this.cancel_time = cancel_time;
	}

	public String getCheck_in() {
		return check_in;
	}

	public void setCheck_in(String check_in) {
		this.check_in = check_in;
	}

	public String getCheck_out() {
		return check_out;
	}

	public void setCheck_out(String check_out) {
		this.check_out = check_out;
	}

	public String getIdcard_id() {
		return idcard_id;
	}

	public void setIdcard_id(String idcard_id) {
		this.idcard_id = idcard_id;
	}

	public int getPrice_id() {
		return price_id;
	}

	public void setPrice_id(int price_id) {
		this.price_id = price_id;
	}

	public int getHouse_state() {
		return house_state;
	}

	public void setHouse_state(int house_state) {
		this.house_state = house_state;
	}

	public int getIs_characteristic() {
		return is_characteristic;
	}

	public void setIs_characteristic(int is_characteristic) {
		this.is_characteristic = is_characteristic;
	}

	public int getIs_choice() {
		return is_choice;
	}

	public void setIs_choice(int is_choice) {
		this.is_choice = is_choice;
	}

	public String getHouse_num() {
		return house_num;
	}

	public void setHouse_num(String house_num) {
		this.house_num = house_num;
	}

	public String getKeyNum() {
		return keyNum;
	}

	public void setKeyNum(String keyNum) {
		this.keyNum = keyNum;
	}

	public String getHouse_name() {
		return house_name;
	}

	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}

	

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getGeo_position() {
		return geo_position;
	}

	public void setGeo_position(String geo_position) {
		this.geo_position = geo_position;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getHouse_ctime() {
		return house_ctime;
	}

	public void setHouse_ctime(String house_ctime) {
		this.house_ctime = house_ctime;
	}

	public int getPricing() {
		return pricing;
	}

	public void setPricing(int pricing) {
		this.pricing = pricing;
	}

	public int getSingle_price() {
		return single_price;
	}

	public void setSingle_price(int single_price) {
		this.single_price = single_price;
	}

	public int getWeek_price() {
		return week_price;
	}

	public void setWeek_price(int week_price) {
		this.week_price = week_price;
	}

	public int getMonth_price() {
		return month_price;
	}

	public void setMonth_price(int month_price) {
		this.month_price = month_price;
	}

	public int getThree_price() {
		return three_price;
	}

	public void setThree_price(int three_price) {
		this.three_price = three_price;
	}

	public int getFive_price() {
		return five_price;
	}

	public void setFive_price(int five_price) {
		this.five_price = five_price;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getMerchants_id() {
		return merchants_id;
	}

	public void setMerchants_id(int merchants_id) {
		this.merchants_id = merchants_id;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	
	
	
	
	
}
