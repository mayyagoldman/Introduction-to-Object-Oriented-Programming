/**
 * The RendererFactory class generating and facilitating the correct type of renderer
 */
public class RendererFactory
{
    /**
     * This method recieves a Renderer type and creates it
     * @param renderer_type name string of desired renderer
     * @return Renderer , specific player while creating an instance of the desired Renderer type. if invalid type returns
     * null
     */
    public Renderer buildRenderer(String renderer_type)
    {
        switch(renderer_type) {
            case "console":
                return new ConsoleRenderer();
            case "none":
                return new VoidRenderer();
            default:
                return null;
        }
    }
}
