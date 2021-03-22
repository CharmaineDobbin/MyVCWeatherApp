package model;

import java.util.List;

public class Root
{
    private Headline Headline;
    private List<DailyForecasts> DailyForecasts;

    public void setHeadline(Headline headline)
    {
        this.Headline = headline;
    }

    public Headline getHeadline()
    {
        return Headline;
    }

    public void setDailyForecasts(List<DailyForecasts> DailyForecasts)
    {
        this.DailyForecasts = DailyForecasts;
    }

    public List<DailyForecasts> getDailyForecasts()
    {
        return this.DailyForecasts;
    }
}
