import { describe, it, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import Card from "../../components/Card.jsx";

describe("Card", () => {
  it("renders children content", () => {
    render(<Card><p>Hello World</p></Card>);
    expect(screen.getByText("Hello World")).toBeInTheDocument();
  });

  it("applies the card class", () => {
    const { container } = render(<Card>Content</Card>);
    expect(container.firstChild).toHaveClass("card");
  });

  it("appends custom className", () => {
    const { container } = render(<Card className="custom">Content</Card>);
    expect(container.firstChild).toHaveClass("card");
    expect(container.firstChild).toHaveClass("custom");
  });
});
