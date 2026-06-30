import { describe, it, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import AppFooter from "../../components/AppFooter.jsx";

describe("AppFooter", () => {
  it("renders the disclaimer text", () => {
    render(<AppFooter />);
    expect(screen.getByText("Sitio web no afiliado con la UFRO")).toBeInTheDocument();
  });

  it("renders as a footer element", () => {
    const { container } = render(<AppFooter />);
    expect(container.querySelector("footer")).not.toBeNull();
  });
});
