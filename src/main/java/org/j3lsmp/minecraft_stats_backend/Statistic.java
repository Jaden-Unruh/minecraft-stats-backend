package org.j3lsmp.minecraft_stats_backend;

public record Statistic(String category, String key) {
	
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (!(other instanceof Statistic))
			return false;
		Statistic stat = (Statistic) other;
		if (stat.category.equals(this.category) && stat.key.equals(this.key))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return key;
	}
}