package com.douniu.imshh.sys.domain;

import java.util.List;

public class JSTree {
	private String id;
	private String text;
	private String icon = "false";
	private State state;
	
	private List<JSTree> children;
	
	public JSTree(){super();}
		
	public JSTree(String id, String text){
		this(id, text, "false", false, false);
	}
	
	public JSTree(String id, String text, String icon, boolean opened, boolean checked) {
		super();
		this.id = id;
		this.text = text;
		this.icon = icon;
		this.state = new State(opened, checked);
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<JSTree> getChildren() {
		return children;
	}
	public void setChildren(List<JSTree> children) {
		this.children = children;
	}		
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public void checkNode(){
		this.state.selected = true;
		this.state.checked = true;
	}
	
	public void uncheckNode(){
		this.state.selected = false;
		this.state.checked = false;
	}
	
	public void opendNode(){
		this.state.opened = true;
	}

	
	
	@Override
	public String toString() {
		return "JSTree [id=" + id + ", text=" + text + ", icon=" + icon + ", state=" + state + ", children=" + children
				+ "]";
	}

	public class State{
		private boolean opened;
		private boolean disabled;
		private boolean selected;
		private boolean checked;
		private boolean undetermined;
			
		public State(){super();}
		
		public State(boolean opened, boolean checked){
			this(opened, false, checked, checked, false);
		}
		
		public State(boolean opened, boolean disabled, boolean selected, boolean checked, boolean undetermined) {
			super();
			this.opened = opened;
			this.disabled = disabled;
			this.selected = selected;
			this.checked = checked;
			this.undetermined = undetermined;
		}
		
		
		public boolean isOpened() {
			return opened;
		}
		public void setOpened(boolean opened) {
			this.opened = opened;
		}
		public boolean isDisabled() {
			return disabled;
		}
		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		public boolean isUndetermined() {
			return undetermined;
		}

		public void setUndetermined(boolean undetermined) {
			this.undetermined = undetermined;
		}

		@Override
		public String toString() {
			return "State [opened=" + opened + ", disabled=" + disabled + ", selected=" + selected + ", checked="
					+ checked + ", undetermined=" + undetermined + "]";
		}

		
		
	}
}
