import { describe, it, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import ClassBlock from "../../components/ClassBlock.jsx";

describe("ClassBlock", () => {
  it("renders code and name", () => {
    render(<ClassBlock code="INF221" name="Programación" status="pendiente" />);
    expect(screen.getByText("INF221")).toBeInTheDocument();
    expect(screen.getByText("Programación")).toBeInTheDocument();
  });

  it("applies the correct status class", () => {
    const { container } = render(
      <ClassBlock code="INF" name="Test" status="presente" />
    );
    expect(container.firstChild).toHaveClass("bloque-clase");
    expect(container.firstChild).toHaveClass("presente");
  });

  it("applies ausente status class", () => {
    const { container } = render(
      <ClassBlock code="INF" name="Test" status="ausente" />
    );
    expect(container.firstChild).toHaveClass("ausente");
  });
});
