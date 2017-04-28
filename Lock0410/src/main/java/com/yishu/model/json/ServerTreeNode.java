package com.yishu.model.json;

import java.util.List;

public class ServerTreeNode {

	public String id;
	public String text;
	public List<ServerTreeNode> children;
	
    public ServerTreeNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServerTreeNode(String id, String text) {
        super();
        this.id = id;
        this.text = text;
    }
	
}
