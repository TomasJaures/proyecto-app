function MyButton(){
    const mystyle = {
        width: "20%"
    };
    return (

        

        <div style={mystyle}>
            
            <button className="btn btn-red">
                Button
            </button>
            <button className="btn btn-darkgreen">
                Button
            </button>
            <button className="btn btn-lightgreen">
                Button
            </button>
            <button className="btn btn-white">
                Button
            </button>
        </div>

        
    );
}

export default MyButton;