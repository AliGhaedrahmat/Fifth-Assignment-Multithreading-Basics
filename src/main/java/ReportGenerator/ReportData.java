package ReportGenerator;

public class ReportData implements Comparable<ReportData> {
    public String year;
    public double totalCost;

    public ReportData(String year, double totalCost) {
        this.year = year;
        this.totalCost = totalCost;
    }

    @Override
    public int compareTo(ReportData other) {
        return Integer.parseInt(this.year) - Integer.parseInt(other.year);
    }
}