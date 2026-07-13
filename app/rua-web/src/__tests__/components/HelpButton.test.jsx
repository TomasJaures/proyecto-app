import { describe, it, expect, vi } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import HelpButton from "../../components/HelpButton.jsx";

describe("HelpButton", () => {
  it("renders the ? text", () => {
    render(<HelpButton onClick={() => {}} />);
    expect(screen.getByText("?")).toBeInTheDocument();
  });

  it("calls onClick when clicked", () => {
    const onClick = vi.fn();
    render(<HelpButton onClick={onClick} />);
    fireEvent.click(screen.getByText("?"));
    expect(onClick).toHaveBeenCalledOnce();
  });

  it("has accessible aria-label", () => {
    render(<HelpButton onClick={() => {}} />);
    expect(screen.getByLabelText("Ayuda")).toBeInTheDocument();
  });
});
